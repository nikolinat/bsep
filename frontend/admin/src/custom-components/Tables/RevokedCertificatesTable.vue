<template>
  <div>
    <Table>
      <TableHead :columnNames="['Entry Name','Certificate Expiry','Algorithm','Key Size',' ',]"></TableHead>
      <TableBody>
        <TableRow
          v-for="(certificate, i) in certificates"
          :key="i"
          :values="[certificate.alias, formatDate(certificate.endDate), 'RSA', '2048','',]">
          <div class="pull-right text-gray">
            <DropDownMenu>
              <ModalOpener :modalBoxId="'moreDetails'">
                <DropDownItem @click="selectedCertificate = certificate">More details</DropDownItem>
              </ModalOpener>
            </DropDownMenu>
          </div>
        </TableRow>
      </TableBody>
    </Table>

    <Modal modalBoxId="moreDetails" title="More details" :sizeClass="'modal-lg'">
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
import CertificateDetailsForm from "../../custom-components/Forms/CertificateDetailsForm.vue";
import ExtensionsModal from '../../custom-components/Modals/ExtensionsModal.vue'
import { mapGetters } from "vuex";
import moment from "moment";

const $ = window.$;

export default {
  props: {
    certificates: {},
  },
  data: () => {
    return {
      selectedCertificate: null,
      extensions: null,
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
    CertificateDetailsForm,
    ExtensionsModal
  },
  computed: {
    ...mapGetters({
      result: "certificates/getResult",
    }),
  },
  methods: {
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