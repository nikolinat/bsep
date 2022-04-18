<template>
  <div>
    <Table>
      <TableHead
        :columnNames="[
          'Email',
          'Common Name',
          'Organization',
          'Organization Unit',
          'Given Name',
          'Surname',
          'Country',
          'User Id',
          '',
        ]"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="(csr, i) in certificateSigningRequests"
          :key="i"
          :values="[
            csr.email,
            csr.commonName,
            csr.organization,
            csr.organizationUnit,
            csr.givenName,
            csr.surname,
            csr.country,
            csr.userId,
          ]"
        >
          <div class="pull-right text-gray">
            <DropDownMenu>
              <DropDownItem @click="onAcceptSubmit(csr)">Accept</DropDownItem>
              <ModalOpener :modalBoxId="'declineCSR'">
                <DropDownItem @click="selectedCSR = csr">Decline</DropDownItem>
              </ModalOpener>
            </DropDownMenu>
          </div>
        </TableRow>
      </TableBody>
    </Table>

    <Modal modalBoxId="declineCSR" title="Decline CSR">
      <div slot="body">
        <div class="col-12">
          <text-input label="Reason" v-model="reason" type="text" />
        </div>
      </div>

      <div slot="buttons">
        <ModalCloser id="declineCSRModalCloser">
          <Button @click="onDeclineSubmit"> Decline </Button>
        </ModalCloser>
      </div>
    </Modal>
  </div>
</template>

<script>
import Table from "../../generic-components/Table/Table.vue";
import TableBody from "../../generic-components/Table/TableBody.vue";
import TableHead from "../../generic-components/Table/TableHead.vue";
import TableRow from "../../generic-components/Table/TableRow.vue";
import ModalOpener from "../../generic-components/Modal/ModalOpener.vue";
import Modal from "../../generic-components/Modal/Modal.vue";
import DropDownMenu from "../../generic-components/DropdownMenu/DropdownMenu.vue";
import DropDownItem from "../../generic-components/DropdownMenu/DropdownItem.vue";
import Button from "../../generic-components/Form/Button.vue";
import TextInput from "../../generic-components/Form/TextInput.vue";
import ModalCloser from "../../generic-components/Modal/ModalCloser.vue";
import toastr from "toastr";
import { mapActions, mapGetters } from "vuex";
export default {
  props: { certificateSigningRequests: {} },
  components: {
    Table,
    TableBody,
    TableHead,
    TableRow,
    Modal,
    ModalOpener,
    ModalCloser,
    DropDownMenu,
    DropDownItem,
    TextInput,
    Button,
  },
  data: () => {
    return {
      selectedCSR: null,
      reason: "",
    };
  },
  computed: {
    ...mapGetters({
      result: "csr/getResult",
    }),
  },
  watch: {
    result({ message, ok, label }) {
      if (label === "decline") {
        if (ok) {
          toastr.success(message);
          this.fetchCsr();
        } else {
          toastr.error(message);
        }
      }
    },
  },
  methods: {
    ...mapActions({
      declineCsr: "csr/declineCsr",
      fetchCsr: "csr/fetchCsr",
    }),
    onDeclineSubmit() {
      this.declineCsr({
        id: this.selectedCSR.id,
        reason: { reasonForDeclining: this.reason },
      });
      document.getElementById("declineCSRModalCloser").click();
    },
    onAcceptSubmit(csr) {
      this.selectedCSR = csr;
      this.$router.push(`/create-certificate/${this.selectedCSR.id}`);
    },
  },
};
</script>
