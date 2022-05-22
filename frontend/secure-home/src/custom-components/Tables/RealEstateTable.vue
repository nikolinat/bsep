<template>
  <div>
    <Table>
      <TableHead
        :columnNames="[
          'Id',
          'Name',
          'Owner',
          ' ',
          ' '
        ]"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="(realEstate, i) in realEstates"
          :key="i"
          :values="[
            realEstate.id,
            realEstate.name,
            realEstate.owner.name + ' '+ realEstate.owner.lastName,
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
            <TenantTable :users="selectedRealEstate.tenants"></TenantTable>
      </div>
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
    TenantTable
  },

  computed: {
    ...mapGetters({
      users: "users/getUsers",
    }),
  },
  watch: {
      users(newUsers) {
        this.allUsers = newUsers;
      }
  },
  methods: {
    ...mapActions({
      fetchRealEstates: "realestate/fetchRealEstates",
      fetchUsers: "users/fetchUsers"
    }),
  },
  mounted() {
    this.fetchUsers(this.searchFilterDto);
    }
};
</script>
