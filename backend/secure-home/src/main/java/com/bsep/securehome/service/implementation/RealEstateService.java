package com.bsep.securehome.service.implementation;

import com.bsep.securehome.dto.UpdateRealEstateDto;
import com.bsep.securehome.exception.BadLogicException;
import com.bsep.securehome.exception.MissingEntityException;
import com.bsep.securehome.model.RealEstate;
import com.bsep.securehome.model.Role;
import com.bsep.securehome.model.User;
import com.bsep.securehome.repository.RealEstateRepository;
import com.bsep.securehome.repository.RoleRepository;
import com.bsep.securehome.service.contract.IRealEstateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RealEstateService implements IRealEstateService {

    private RealEstateRepository realEstateRepository;
    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public RealEstateService(RealEstateRepository realEstateRepository, UserService userService, RoleRepository roleRepository) {
        this.realEstateRepository = realEstateRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RealEstate> findAll() {
        return realEstateRepository.findAll();
    }

    @Override
    public RealEstate update(UpdateRealEstateDto updateRealEstateDto) {
        RealEstate realEstate = realEstateRepository.findById(updateRealEstateDto.getId()).orElse(null);
        if (realEstate == null) {
            throw new MissingEntityException("Real estate with given id does not exist in the system.");
        }
        User user = userService.findUser(updateRealEstateDto.getUsername());
        Role role = roleRepository.findById(Long.parseLong(updateRealEstateDto.getRole())).get();
        if (role.getName().equals("ROLE_HOUSE_OWNER")) {
            List<Role> roleList = user.getRoles().stream().filter(r -> r.getName().equals("ROLE_HOUSE_OWNER")).collect(Collectors.toList());
            if (roleList.size() != 0) {
                List<User> owners = realEstate.getOwners().stream().filter(owner -> owner.getUsername().equals(user.getUsername())).collect(Collectors.toList());
                List<User> tenants = realEstate.getTenants().stream().filter(tenant -> tenant.getUsername().equals(user.getUsername())).collect(Collectors.toList());
                if(owners.size() == 0) {
                    if(tenants.size() != 0){
                        realEstate.getTenants().remove(user);
                    }
                    realEstate.getOwners().add(user);
                }else {
                    throw new BadLogicException("User is already owner of this real estate!");
                }
            } else {
                throw new BadLogicException("You need to change role for this user to owner.");
            }

        }else if(role.getName().equals("ROLE_TENANT")){
            List<Role> roleList = user.getRoles().stream().filter(r -> r.getName().equals("ROLE_TENANT") ).collect(Collectors.toList());
            if (roleList.size() != 0) {
                List<User> tenants = realEstate.getTenants().stream().filter(tenant -> tenant.getUsername().equals(user.getUsername())).collect(Collectors.toList());
                List<User> owners = realEstate.getOwners().stream().filter(owner -> owner.getUsername().equals(user.getUsername())).collect(Collectors.toList());  
                if(tenants.size() == 0) {
                    if(owners.size() != 0){
                        realEstate.getOwners().remove(user);
                    }
                    realEstate.getTenants().add(user);
                }else {
                    throw new BadLogicException("User is already tenant in this real estate!");
                }
            } else {
                throw new BadLogicException("You need to change role for this user to tenant or owner.");
            }
        }
        realEstateRepository.save(realEstate);
        return realEstate;
    }

    @Override
    public RealEstate removeTenant(Integer id, Long realEstateId) throws Exception {
        RealEstate realEstate = realEstateRepository.findById(realEstateId).orElse(null);
        User user = userService.findById(id);
        if(realEstate.getOwners().contains(user)){
            realEstate.getOwners().remove(user);
        }else if(realEstate.getTenants().contains(user)){
            realEstate.getTenants().remove(user);
        }

        return realEstateRepository.save(realEstate);
    }
}
