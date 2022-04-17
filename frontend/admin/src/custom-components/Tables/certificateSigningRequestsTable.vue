<template>
  <div>
    <Table>
      <TableHead
        :columnNames="[
          'email',
          'commonName',
          'organization',
          'organizationUnit',
          'givenName',
          'surname',
          'country',
          'userId',
          'isAccepted',
          '',
          '',
        ]"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="p in certificateSigningRequests"
          :key="p.id"
          :values="[
            p.email,
            p.commonName,
            p.organization,
            p.organizationUnit,
            p.givenName,
            p.surname,
            p.country,
            p.userId,
            p.isAccepted,
          ]"
        >
        </TableRow>
        <Pagination @pageChange="$emit('pageChange', $event)" />
      </TableBody>
    </Table>
  </div>
</template>

<script>
import Table from "../../generic-components/Table/Table.vue";
import TableBody from "../../generic-components/Table/TableBody.vue";
import TableHead from "../../generic-components/Table/TableHead.vue";
import TableRow from "../../generic-components/Table/TableRow.vue";
import Pagination from "../../generic-components/Table/Pagination.vue";
import { mapGetters } from "vuex";

export default {
  props: { certificateSigningRequests: {} },
  components: { Table, TableBody, TableHead, TableRow, Pagination },

  //   methods: {
  //     ...mapActions({
  //       fetchCsr: "csr/fetchCsr",
  //     }),
  //   },
  //   mounted() {
  //     this.fetchCsr();
  //   },

  computed: {
    ...mapGetters({
      result: "csr/getResult",
    }),
  },
};
</script>
