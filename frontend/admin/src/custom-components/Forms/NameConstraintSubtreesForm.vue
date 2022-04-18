<template>
  <div>
    <div>
      <ul class="pagination">
        <li class="paginate_button page-item next" id="datatables_next">
          <div>
            <add-subtrees-name-policy-modal :modalBoxId="modalBoxId" />

            <ModalOpener :modalBoxId="modalBoxId">
              <Button className="btn btn-sm btn-default">Add</Button>
            </ModalOpener>
          </div>
        </li>
        <!-- <li class="paginate_button page-item next" id="datatables_next">
          <Button className="btn btn-sm btn-default">Edit</Button>
        </li>
        <li class="paginate_button page-item next" id="datatables_next">
          <Button className="btn btn-sm btn-default">Delete</Button>
        </li> -->
      </ul>
    </div>
    <Table>
      <TableHead :columnNames="['Base', 'Minimum', 'Maximum']"></TableHead>
      <TableBody>
        <TableRow
          v-for="p in subtrees"
          :key="p.id"
          :values="[p.base, p.min, p.max]"
        >
        </TableRow>
      </TableBody>
    </Table>
    <br /><br />
  </div>
</template>

<script>
import Button from "../../generic-components/Form/Button.vue";
import Table from "../../generic-components/Table/Table.vue";
import TableBody from "../../generic-components/Table/TableBody.vue";
import TableHead from "../../generic-components/Table/TableHead.vue";
import TableRow from "../../generic-components/Table/TableRow.vue";
import { mapGetters } from "vuex";
import addSubtreesNamePolicyModal from "../Modals/AddSubtreesNamePolicyModal.vue";
import ModalOpener from "../../generic-components/Modal/ModalOpener.vue";

export default {
  components: {
    Table,
    TableBody,
    TableHead,
    TableRow,
    Button,
    addSubtreesNamePolicyModal,
    ModalOpener,
  },

  computed: {
    ...mapGetters({
      //result: "csr/getResult",
    }),
  },
  props: {
    subtrees: {
      type: Array,
      default: () => [],
    },
    modalBoxId: {
      type: String,
    },
  },
  methods: {
    remove(index) {
      this.addedOptions.splice(index, 1);
    },

    handleAddGeneralNameToAuthorityKeyIdentifier(arg) {
      this.addedOptions.push({
        label: arg.label,
        value: arg.value,
        enteredValue: arg.enteredValue,
      });
    },
  },
};
</script>
