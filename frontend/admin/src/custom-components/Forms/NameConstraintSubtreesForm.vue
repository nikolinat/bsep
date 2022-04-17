<template>
  <div>
    <div>
      <ul class="pagination">
        <li class="paginate_button page-item next" id="datatables_next">
          <Button className="btn btn-sm btn-default">Add</Button>
        </li>
        <li class="paginate_button page-item next" id="datatables_next">
          <Button className="btn btn-sm btn-default">Edit</Button>
        </li>
        <li class="paginate_button page-item next" id="datatables_next">
          <Button className="btn btn-sm btn-default">Delete</Button>
        </li>
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
        <Pagination @pageChange="$emit('pageChange', $event)" />
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
import Pagination from "../../generic-components/Table/Pagination.vue";
import { mapGetters } from "vuex";

export default {
  props: { subtrees: {} },
  components: { Table, TableBody, TableHead, TableRow, Pagination, Button },

  computed: {
    ...mapGetters({
      //result: "csr/getResult",
    }),
  },
};
</script>
