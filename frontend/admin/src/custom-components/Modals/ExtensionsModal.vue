<template>
    <Modal :modalBoxId="modalBoxId" title="Certificate extensions" :sizeClass="'modal-lg'">
        <div slot="body">
           <Table>
            <TableHead :columnNames="['Name', ' ']"></TableHead>
                <TableBody>
                    <TableRow
                        v-for="(extension, i) in extensions"
                        :key="i"
                        :values="[extension.extensionName]"
                        >
                    <div class="pull-right text-gray">
                        <DropDownMenu>
                            <DropDownItem @click="selectedExtensions = extension">View</DropDownItem>
                        </DropDownMenu>
                    </div>
                    </TableRow>
                </TableBody>
            </Table>

            <div v-if="selectedExtensions !== null">
                <TextArea :value="selectedExtensions.value" :disabled="true" :label="'Extension value:'"></TextArea>
            </div>
        </div>
    </Modal>
    
</template>

<script>
import Table from "../../generic-components/Table/Table.vue";
import TableHead from "../../generic-components/Table/TableHead.vue";
import TableBody from "../../generic-components/Table/TableBody.vue";
import TableRow from "../../generic-components/Table/TableRow.vue";
import Modal from '../../generic-components/Modal/Modal.vue'
import TextArea from '../../generic-components/Form/TextArea.vue';
import DropDownMenu from "../../generic-components/DropdownMenu/DropdownMenu.vue";
import DropDownItem from "../../generic-components/DropdownMenu/DropdownItem.vue";

export default {
  props: {
    extensions: {},
    modalBoxId: {},
  },
  data: () => {
    return {
      selectedExtensions: null,
    };
  },
  mounted() {},
  components: {
    Table,
    TableHead,
    TableBody,
    TableRow,
    Modal,
    TextArea,
    DropDownMenu,
    DropDownItem
  },
  computed: {
  },
  watch: {
    extensions(e) {
      this.extensions = e;
      this.selectedExtensions = null;
    }
  },
  methods: {
  },
};
</script>