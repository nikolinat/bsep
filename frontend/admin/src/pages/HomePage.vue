<template>
  <div class="content">
    <div class="container-fluid">
      <div>
        <b-button variant="success">Add new</b-button>
        <b-button variant="success">Add root</b-button>
        <b-table
          striped
          hover
          :items="items"
          :fields="fields"
          sort-icon-left
          responsive="sm"
        >
          <template #cell(Revoke)>
            <b-icon icon="x-square" font-scale="2"></b-icon>
          </template>
          <template #cell(Extend)>
            <b-icon
              icon="arrow-clockwise"
              animation="spin"
              font-scale="2"
            ></b-icon>
          </template>
        </b-table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      fields: [
        "Entry_Name",
        "Algorithm",
        "Key_Size",
        {
          key: "Certificate_Expiry",
          sortable: true,
        },
        {
          key: "Revoke",
          sortable: false,
          data: "s",
        },
        {
          key: "Extend",
          sortable: false,
        },
      ],
      items: [
        {
          Entry_Name: "root",
          Algorithm: "RSA",
          Key_Size: 2048,
          Certificate_Expiry: "3/23/2024",
        },
        {
          Entry_Name: "intermediate",
          Algorithm: "RSA",
          Key_Size: 2048,
          Certificate_Expiry: "3/23/2024",
        },
        {
          Entry_Name: "certificatea",
          Algorithm: "RSA",
          Key_Size: 2048,
          Certificate_Expiry: "4/11/2023",
        },
        {
          Entry_Name: "certificateb",
          Algorithm: "RSA",
          Key_Size: 2048,
          Certificate_Expiry: "4/1/2023",
        },
      ],
    };
  },
  name: "Home",
  components: {},
  methods: {},
  watch: {},
};
</script>
