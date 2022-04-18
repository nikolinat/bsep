<template>
  <div>
    <Table>
      <TableHead :columnNames="['Entry Name','Certificate Expiry','Algorithm','Key Size',' ',]"></TableHead>
      <TableBody>
        <TableRow
          v-for="(certificate, i) in certificates"
          :key="i"
          :values="[certificate.alias, formatDate(certificate.endDate), 'RSA', '','',]">
          <div class="pull-right text-gray">
            <DropDownMenu>
              <ModalOpener :modalBoxId="'revokeCertificate'">
                <DropDownItem @click="selectedCertificate = certificate">Revoke certificate</DropDownItem>
              </ModalOpener>
              <ModalOpener :modalBoxId="'moreDetailsModalOpener'">
                <DropDownItem @click="selectedCertificate = certificate">More details</DropDownItem>
              </ModalOpener>
            </DropDownMenu>
          </div>
        </TableRow>
      </TableBody>
    </Table>
    <Modal modalBoxId="revokeCertificate" title="Revoke certificate">
      <div slot="body">
        <SelectOptionInput
          :options="reasons"
          v-model="reason"
        ></SelectOptionInput>
        <div class="col-12">
          <text-input label="Other" v-model="other" type="text" />
        </div>
      </div>

      <div slot="buttons">
        <ModalCloser id="revokeModalCloser">
          <Button @click="onRevokeSubmit"> Revoke </Button>
        </ModalCloser>
      </div>
    </Modal>

    <Modal modalBoxId="moreDetailsModalOpener" title="More details" :sizeClass="'modal-lg'">
      <div slot="body" v-if="selectedCertificate !== null">
        <CertificateDetailsForm :certificate="selectedCertificate" @openExtensions="openExtensionsOfCertificate"></CertificateDetailsForm>
      </div>
    </Modal>

    <ExtensionsModal :modalBoxId="'extensionsModalOpener'" :extensions="extensions"></ExtensionsModal>

  </div>

</template>

<script>
import Table from "../../generic-components/Table/Table.vue";
import TableHead from "../../generic-components/Table/TableHead.vue";
import TableBody from "../../generic-components/Table/TableBody.vue";
import TableRow from "../../generic-components/Table/TableRow.vue";
import ModalOpener from "../../generic-components/Modal/ModalOpener.vue";
import Modal from "../../generic-components/Modal/Modal.vue";
import DropDownMenu from "../../generic-components/DropdownMenu/DropdownMenu.vue";
import DropDownItem from "../../generic-components/DropdownMenu/DropdownItem.vue";
import Button from "../../generic-components/Form/Button.vue";
import TextInput from "../../generic-components/Form/TextInput.vue";
import SelectOptionInput from "../../generic-components/Form/SelectOptionInput.vue";
import ModalCloser from "../../generic-components/Modal/ModalCloser.vue";
import CertificateDetailsForm from "../../custom-components/Forms/CertificateDetailsForm.vue";
import toastr from "toastr";
import { mapActions, mapGetters } from "vuex";
import moment from "moment";
import ExtensionsModal from '../../custom-components/Modals/ExtensionsModal.vue'

const $ = window.$;

const reasons = [
  { value: 0, label: "Improperly issued a certificate" },
  { value: 1, label: "Certificate is counterfeit" },
  { value: 2, label: "Private key has been compromised" },
  { value: 3, label: "The issuing CA has been compromised" },
  {
    value: 4,
    label:
      "The certificate owner no longer owns the domain for which it was issued",
  },
  { value: 5, label: "The certificate owner has ceased operations entirely" },
  {
    value: 6,
    label:
      "The original certificate has been replaced with a new certificate from another issuer",
  },
];

export default {
  props: {
    certificates: {},
  },
  data: () => {
    return {
      selectedCertificate: null,
      reasons: reasons,
      reason: "",
      other: null,
      extensions: null
    };
  },
  mounted() {},
  components: {
    Table,
    TableHead,
    TableBody,
    TableRow,
    ModalOpener,
    Modal,
    DropDownItem,
    DropDownMenu,
    Button,
    SelectOptionInput,
    TextInput,
    ModalCloser,
    CertificateDetailsForm,
    ExtensionsModal
  },
  computed: {
    ...mapGetters({
      result: "certificates/getResult",
    }),
  },
  watch: {
    result({ message, ok, label }) {
      if (label === "update") {
        if (ok) {
          toastr.success(message);
          this.fetchValidCertificates();
        } else {
          toastr.error(message);
        }
      }
    },
  },
  methods: {
    ...mapActions({
      revokeCertificate: "certificates/revokeCertificate",
      fetchValidCertificates: "certificates/fetchValidCertificates"
    }),

    getReasonOption() {
      return this.reasons.find(
        (reasonOption) => reasonOption.value === this.reason
      )?.label;
    },

    onRevokeSubmit() {
      let r = "";
      if (this.reason !== "") {
        r = this.getReasonOption();
      } else {
        r = this.other;
      }
      this.revokeCertificate({
        certificate: this.selectedCertificate,
        reason: r,
      });
      document.getElementById("revokeModalCloser").click();
    },
    formatDate(d) {
      return moment(d).format("ll");
    },

    openExtensionsOfCertificate(arg) {
      this.extensions = arg;
      setTimeout(() => {
                $('.modal-dialog').selectpicker('refresh');
            }, 100);
    }
  },
};
</script>