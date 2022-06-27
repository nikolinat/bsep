<template>
  <div>
    <Table>
      <TableHead
        :columnNames="['realEstateId', 'id', 'type', 'name']"
      ></TableHead>
      <TableBody>
        <TableRow
          v-for="(deviceMessage, i) in devicess"
          :key="i"
          :values="[
            deviceMessage.realEstateId,
            deviceMessage.id,
            deviceMessage.type,
            deviceMessage.name,
          ]"
        >
        </TableRow>
      </TableBody>
    </Table>
  </div>
</template>

<script>
import Table from "../../generic-components/Table/Table.vue";
import TableHead from "../../generic-components/Table/TableHead.vue";
import TableBody from "../../generic-components/Table/TableBody.vue";
import TableRow from "../../generic-components/Table/TableRow.vue";
import { mapActions, mapGetters } from "vuex";

export default {
  props: {},
  data: () => {
    return {
      users: [],
      selectedUser: null,
      devicess: [
        {
          realEstateId: 1,
          id: "11",
          type: "camera",
          name: "camera1",
        },
        {
          realEstateId: 1,
          id: "12",
          type: "camera",
          name: "camera2",
        },
      ],
      selectedMessage: null,
    };
  },
  mounted() {},
  components: {
    Table,
    TableHead,
    TableBody,
    TableRow,
  },

  computed: {
    ...mapGetters({
      searchedUsers: "users/getUsers",
      result: "users/getResult",
    }),
  },
  methods: {
    ...mapActions({
      fetchUsers: "users/fetchUsers",
    }),
  },
};
</script>
