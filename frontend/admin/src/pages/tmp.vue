<template>
  <div slot="body">
    <SelectOptionInput
      class="col-12"
      id="selektovan"
      :label="'Base'"
      :showLabel="true"
      :options="options"
      v-model="currentOption"
    />

    <Form
      @submit="onSubmitDirectoryName($event)"
      class="col-10"
      style="margin-left: 4%"
      v-if="
        currentOption !== null &&
        currentOption !== '' &&
        currentOption === 'DirectoryName'
      "
    >
      <form-row>
        <text-input
          v-model="directoryName.CN"
          type="text"
          label="Common Name"
        />
        <text-input
          style="margin-left: 15%"
          v-model="directoryName.OU"
          type="text"
          label="Organization Unit"
        />
      </form-row>
      <form-row>
        <text-input
          v-model="directoryName.ON"
          type="text"
          label="Organization Name"
        />
        <text-input
          style="margin-left: 15%"
          v-model="directoryName.L"
          type="text"
          label="Locality Name"
        />
      </form-row>
      <form-row>
        <text-input v-model="directoryName.ST" type="text" label="State Name" />
        <text-input
          style="margin-left: 15%"
          v-model="directoryName.C"
          type="text"
          label="Country"
        />
      </form-row>
      <form-row>
        <text-input v-model="directoryName.E" type="text" label="Email" />
        <text-input
          style="margin-left: 15%"
          v-model="directoryName.SN"
          type="text"
          label="Serial Number"
        />
      </form-row>
      <form-row>
        <text-input v-model="directoryName.GN" type="text" label="Given Name" />
        <text-input
          style="margin-left: 15%"
          v-model="directoryName.SURNAME"
          type="text"
          label="Surname"
        />
      </form-row>
      <form-row>
        <text-input
          v-model="directoryName.DC"
          type="text"
          label="Domain Component"
        />
        <text-input
          style="margin-left: 15%"
          v-model="directoryName.UID"
          type="text"
          label="User ID"
        />
      </form-row>
      <form-row>
        <text-input v-model="directoryName.NAME" type="text" label="Name" />
        <text-input
          style="margin-left: 15%"
          v-model="directoryName.STREET"
          type="text"
          label="Street"
        />
      </form-row>
      <form-row>
        <text-input v-model="directoryName.T" type="text" label="Title" />
        <text-input
          style="margin-left: 15%"
          v-model="directoryName.INITIALS"
          type="text"
          label="Initials"
        />
      </form-row>
      <form-row>
        <text-input
          v-model="directoryName.PSEUDONYM"
          type="text"
          label="Pseudonym"
        />
        <text-input
          style="margin-left: 15%"
          v-model="directoryName.DN_QUALIFIER"
          type="text"
          label="DN Qualifier"
        />
      </form-row>
      <form-row>
        <text-input
          v-model="directoryName.GENERATION"
          type="text"
          label="Genreation"
        />
      </form-row>
      <form-row>
        <Button @click="showErrorMessage = true" type="submit">Add</Button>
      </form-row>
    </Form>

    <Form
      @submit="onSubmit($event, currentOption)"
      class="col-10"
      style="margin-left: 4%"
      v-else-if="currentOption !== null && currentOption !== ''"
    >
      <form-row>
        <text-input v-model="option.enteredValue" type="text" />
      </form-row>
      <form-row>
        <Button @click="showErrorMessage = true" type="submit">Add</Button>
      </form-row>
    </Form>
  </div>
</template>

<script>
import SelectOptionInput from "../generic-components/Form/SelectOptionInput.vue";
import TextInput from "../generic-components/Form/TextInput.vue";
import FormRow from "../generic-components/Form/FormRow.vue";
import Button from "../generic-components/Form/Button.vue";

export default {
  props: ["modalBoxId"],

  components: {
    SelectOptionInput,
    TextInput,
    FormRow,
    Button,
  },

  data: function () {
    return {
      options: [
        {
          label: "DNS Name",
          value: "DNSName",
        },
        {
          label: "IP Address",
          value: "IPAddress",
        },
        {
          label: "Register ID",
          value: "RegisteredID",
        },
        {
          label: "RFC 822 Name",
          value: "Rfc822Name",
        },
        {
          label: "URI",
          value: "UniformResourceIdentifier",
        },
        {
          label: "Directory Name",
          value: "DirectoryName",
        },
      ],
      option: {
        label: "",
        value: null,
        enteredValue: "",
      },
      currentOption: null,
      showErrorMessage: false,
      directoryName: {
        CN: "",
        OU: "",
        ON: "",
        L: "",
        ST: "",
        C: "",
        E: "",
        SN: "",
        GN: "",
        SURNAME: "",
        DC: "",
        UID: "",
        NAME: "",
        STREET: "",
        T: "",
        INITIALS: "",
        PSEUDONYM: "",
        DN_QUALIFIER: "",
        GENERATION: "",
      },
    };
  },

  watch: {
    currentOption(option) {
      this.currentOption = option;
    },
  },

  methods: {
    onSubmit(e, option) {
      e.preventDefault();
      this.option.value = option;
      this.options.forEach((o) => {
        if (o.value === option) {
          this.option.label = o.label;
        }
      });

      this.$emit("newGeneralName", this.option);
    },

    onSubmitDirectoryName(e) {
      e.preventDefault();
      this.option.value = "DirectoryName";
      this.option.label = "Directory Name";
      let enteredValue = "";
      let directoryNameMap = new Map(Object.entries(this.directoryName));
      directoryNameMap.forEach((value, key) => {
        if (value !== "") {
          enteredValue += key + "=" + value + ",";
        }
      });
      this.option.enteredValue = enteredValue.substring(
        0,
        enteredValue.length - 1
      );

      this.$emit("newGeneralName", this.option);
    },
  },

  mounted() {},
};
</script>
