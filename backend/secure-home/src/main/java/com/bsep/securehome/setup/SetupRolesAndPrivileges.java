package com.bsep.securehome.setup;

import com.bsep.securehome.model.Privilege;
import com.bsep.securehome.model.RealEstate;
import com.bsep.securehome.model.Role;
import com.bsep.securehome.model.User;
import com.bsep.securehome.repository.PrivilegeRepository;
import com.bsep.securehome.repository.RealEstateRepository;
import com.bsep.securehome.repository.RoleRepository;
import com.bsep.securehome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class SetupRolesAndPrivileges implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readCertificates = createPrivilegeIfNotFound("READ_CERTIFICATES");
        Privilege editCertificate = createPrivilegeIfNotFound("EDIT_CERTIFICATE");
        Privilege readUsers = createPrivilegeIfNotFound("READ_USERS");
        Privilege writeUsers = createPrivilegeIfNotFound("WRITE_USERS");
        Privilege removeUsers = createPrivilegeIfNotFound("REMOVE_USERS");
        Privilege readCsr = createPrivilegeIfNotFound("READ_CSR");
        Privilege writeCsr = createPrivilegeIfNotFound("WRITE_CSR");
        Privilege editCsr = createPrivilegeIfNotFound("EDIT_CSR");
        Privilege readRealEstate = createPrivilegeIfNotFound("READ_REAL_ESTATES");
        Privilege readDevice = createPrivilegeIfNotFound("READ_DEVICES");
        Privilege createDevice = createPrivilegeIfNotFound("CREATE_DEVICE");
        Privilege createAlarm = createPrivilegeIfNotFound("CREATE_ALARM");
        Privilege readAlarms = createPrivilegeIfNotFound("READ_ALARMS");
        Privilege readRealEstatesByTenantOwner = createPrivilegeIfNotFound("READ_REAL_ESTATES_BY_TENANT_OWNER");
        Privilege readLogs = createPrivilegeIfNotFound("READ_LOGS");
        Privilege createRule = createPrivilegeIfNotFound("CREATE_RULE");
        Privilege createRuleLog = createPrivilegeIfNotFound("CREATE_RULE_LOG");
        Privilege createReport = createPrivilegeIfNotFound("CREATE_REPORT");

        List<Privilege> adminPrivileges = Arrays.asList(readCertificates, editCertificate, readUsers, writeUsers,
                readCsr, editCsr, readRealEstate, removeUsers, readDevice, createDevice, createAlarm, readAlarms,
                readCertificates, editCertificate, readUsers, readCsr, editCsr,
                writeCsr, readLogs, createRule, createRuleLog, createReport);
        List<Privilege> ownerPrivileges = Arrays.asList(writeCsr, removeUsers, readDevice, createAlarm, readAlarms,
                readRealEstatesByTenantOwner, createRule, createReport);
        List<Privilege> tenantPrivileges = Arrays.asList(readDevice, readAlarms, readRealEstatesByTenantOwner);

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_HOUSE_OWNER", ownerPrivileges);
        createRoleIfNotFound("ROLE_TENANT", tenantPrivileges);

        addAdmin();
        addOwner();
        addTenant();
        addRealEstates();
        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    void createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
    }

    @Transactional
    void addAdmin() {
        if (userRepository.findByUsername("mirko123") == null) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            User user = new User();
            user.setEmailAddress("mirkomiric@gmail.com");
            user.setName("mirko");
            user.setLastName("miric");
            user.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
            user.setUsername("mirko123");
            user.setSalt("0e4ace1c-d09e-11ec-9d64-0242ac120002".getBytes());
            user.setRoles(Collections.singletonList(adminRole));
            user.setDeleted(false);
            userRepository.save(user);
        }
    }

    @Transactional
    void addOwner() {
        if (userRepository.findByUsername("pera123") == null) {
            Role ownerRole = roleRepository.findByName("ROLE_HOUSE_OWNER");
            User user = new User();
            user.setEmailAddress("peraperic@gmail.com");
            user.setName("pera");
            user.setLastName("peric");
            user.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
            user.setUsername("pera123");
            user.setSalt("0e4ace1c-d09e-11ec-9d64-0242ac120002".getBytes());
            user.setRoles(Collections.singletonList(ownerRole));
            user.setDeleted(false);
            userRepository.save(user);
        }
    }

    @Transactional
    void addTenant() {
        if (userRepository.findByUsername("zika123") == null) {
            Role tenantRole = roleRepository.findByName("ROLE_TENANT");
            User user = new User();
            user.setEmailAddress("zikazikic@gmail.com");
            user.setName("zika");
            user.setLastName("zikic");
            user.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
            user.setUsername("zika123");
            user.setSalt("0e4ace1c-d09e-11ec-9d64-0242ac120002".getBytes());
            user.setRoles(Collections.singletonList(tenantRole));
            user.setDeleted(false);
            userRepository.save(user);
        }
        if (userRepository.findByUsername("sima123") == null) {
            Role tenantRole = roleRepository.findByName("ROLE_TENANT");
            User user = new User();
            user.setEmailAddress("simasimic@gmail.com");
            user.setName("sima");
            user.setLastName("simic");
            user.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
            user.setUsername("sima123");
            user.setSalt("0e4ace1c-d09e-11ec-9d64-0242ac120002".getBytes());
            user.setRoles(Collections.singletonList(tenantRole));
            user.setDeleted(false);
            userRepository.save(user);
        }

    }

    @Transactional
    void addRealEstates() {

        if (!realEstateRepository.findById(1L).isPresent()) {
            RealEstate realEstate = new RealEstate("Kuca", "Kisacka 1");
            ArrayList<User> owners = new ArrayList<>();
            owners.add(userRepository.findByUsername("pera123"));
            realEstate.setOwners(owners);
            realEstate.setTenants(Collections.singletonList(userRepository.findByUsername("zika123")));
            realEstateRepository.save(realEstate);

        }
        if (!realEstateRepository.findById(2L).isPresent()) {
            RealEstate realEstate = new RealEstate("Stan", "Marka Kraljevica 5");
            ArrayList<User> owners = new ArrayList<>();
            owners.add(userRepository.findByUsername("pera123"));
            realEstate.setOwners(owners);
            realEstate.setTenants(new ArrayList<>());
            realEstateRepository.save(realEstate);

        }
    }
}