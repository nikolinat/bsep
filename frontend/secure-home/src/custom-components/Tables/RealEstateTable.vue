<template>
  <div>
    <Table>
      <TableHead
        :columnNames="[
          'Id',
          'Name',
          'Address',
          '',
          ''
        ]"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="(realEstate, i) in realEstates"
          :key="i"
          :values="[
            realEstate.id,
            realEstate.name,
            realEstate.address,
            '',
          ]"
        >
          <div class="pull-right text-gray">
            <DropDownMenu>
              <ModalOpener :modalBoxId="'addOwnerOrTenant'">
                <DropDownItem @click="selectedRealEstate = realEstate"
                  >Add owner/tenant</DropDownItem
                >
              </ModalOpener>
              <ModalOpener :modalBoxId="'viewTenantsModalOpener'">
                <DropDownItem @click="selectedRealEstate = realEstate"
                  >View tenants</DropDownItem
                >
              </ModalOpener>
                <ModalOpener :modalBoxId="'viewOwnersModalOpener'">
                <DropDownItem @click="selectedRealEstate = realEstate"
                  >View owners</DropDownItem
                >
              </ModalOpener>
               <ModalOpener :modalBoxId="'addDeviceModalOpener'">
                <DropDownItem @click="selectedRealEstate = realEstate"
                  >Add device</DropDownItem
                >
              </ModalOpener>
              <DropDownItem @click="viewDevice(realEstate)"> View devices </DropDownItem>
              <ModalOpener :modalBoxId="'addAlarmModalOpener'">
                <DropDownItem @click="selectedRealEstate = realEstate"
                  >Add alarm</DropDownItem
                >
              </ModalOpener>
               <DropDownItem @click="viewAlarms(realEstate)"> View alarms </DropDownItem>
            </DropDownMenu>
          </div>
        </TableRow>
      </TableBody>
    </Table>
    <Modal modalBoxId="addOwnerOrTenant" title="Add owner/tenant" :sizeClass="'modal-lg'">
      <div slot="body">
        <SelectOptionInput class="col-6" label="Select user role" :showLabel="false" :options="roles" v-model="role"/>
        <br>
        <AddOwnerTenantTable :users="allUsers" :role="role" :realEstate="selectedRealEstate"></AddOwnerTenantTable>
      </div>
       <div slot="buttons">
        <ModalCloser id="addOwnerOrTenantCloser">
          <Button> Close </Button>
        </ModalCloser>
      </div>
    </Modal>

    <Modal
      modalBoxId="viewTenantsModalOpener"
      title="Tenants"
      :sizeClass="'modal-lg'">
      <div slot="body" v-if="selectedRealEstate !== null">
        <TenantTable :users="selectedRealEstate.tenants" :realEstate="selectedRealEstate"></TenantTable>
      </div>
    </Modal>
    <Modal
      modalBoxId="viewOwnersModalOpener"
      title="Owners"
      :sizeClass="'modal-lg'">
      <div slot="body" v-if="selectedRealEstate !== null">
        <TenantTable :users="selectedRealEstate.owners" :realEstate="selectedRealEstate"></TenantTable>
      </div>
    </Modal>

    <Modal
      modalBoxId="addDeviceModalOpener"
      title="Add device"
      :sizeClass="'modal-sg'">
      <div slot="body" v-if="selectedRealEstate !== null">
        <AddDeviceForm :realEstateId="selectedRealEstate.id"/>
      </div>
        <ModalCloser id="addDeviceModalCloser"></ModalCloser>
    </Modal>

     <Modal
      modalBoxId="addAlarmModalOpener"
      title="Add alarm"
      :sizeClass="'modal-sg'">
      <div slot="body" v-if="selectedRealEstate !== null">
        <AddAlarmForm :realEstateId="selectedRealEstate.id"/>
      </div>
        <ModalCloser id="addAlarmModalCloser"></ModalCloser>
    </Modal>   
  </div>
</template>

<script>
import Table from "../../generic-components/Table/Table.vue";
import TableHead from "../../generic-components/Table/TableHead.vue";
import TableBody from "../../generic-components/Table/TableBody.vue";
import TableRow from "../../generic-components/Table/TableRow.vue";
import ModalOpener from "../../generic-components/Modal/ModalOpener.vue";
import Modal from "../../generic-components/Modal/Modal.vue";
import ModalCloser from "../../generic-components/Modal/ModalCloser.vue";
import DropDownMenu from "../../generic-components/DropdownMenu/DropdownMenu.vue";
import DropDownItem from "../../generic-components/DropdownMenu/DropdownItem.vue";
import AddOwnerTenantTable from "../../custom-components/Tables/AddOwnerTenantTable.vue";
import SelectOptionInput from '../../generic-components/Form/SelectOptionInput.vue';
import Button from "../../generic-components/Form/Button.vue";
import TenantTable from "../../custom-components/Tables/TenantTable.vue";
import AddDeviceForm from '../../custom-components/Forms/AddDeviceForm.vue'
import AddAlarmForm from '../../custom-components/Forms/AddAlarmForm.vue'

import { mapActions, mapGetters } from "vuex";

export default {
  props: {
    realEstates: {},
  },
  data: () => {
    return {
      selectedRealEstate: null,
      allUsers: [],
      role: '',
      searchFilterDto: {
                name: "",
                lastName: "",
                username: "",
                email: "",
                roles: [1, 2, 3]

      },
      roles: [
        {
          label: "ROLE_HOUSE_OWNER",
          value: 2,
        },
        {
          label: "ROLE_TENANT",
          value: 3,
        },
      ],
    };
  },
  components: {
    Table,
    TableHead,
    TableBody,
    TableRow,
    ModalOpener,
    Modal,
    DropDownItem,
    DropDownMenu,
    AddOwnerTenantTable,
    SelectOptionInput,
    ModalCloser,
    Button,
    TenantTable,
    AddDeviceForm,
    AddAlarmForm
  },

  computed: {
    ...mapGetters({
      users: "users/getUsers",
      getRealEstate: "realestate/getRealEstate"
    }),
  },
  watch: {
      users(newUsers) {
        this.allUsers = newUsers;
      },
       getRealEstate(newRealEstates){
        this.selectedRealEstate = newRealEstates;
      }
  },
  methods: {
    ...mapActions({
      fetchRealEstates: "realestate/fetchRealEstates",
      fetchOwnersAndTenants: "users/fetchOwnersAndTenants"
    }),

    viewDevice(realEstate){
       this.$router.push(`/devices/${realEstate.id}`);
    },

    viewAlarms(realEstate){
      this.$router.push(`/alarms/${realEstate.id}`);
    }
  },
  mounted() {
    this.fetchOwnersAndTenants();
    }
};
</script>
