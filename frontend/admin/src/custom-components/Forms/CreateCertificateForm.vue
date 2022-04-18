<template>
  <Form @submit="onSubmit($event)">
    <form-group>
      <form-row>
        <div class="col-6">
          <DateTimePicker
            v-model="certificate.startDate"
            :isValid="!!certificate.startDate"
            :showErrorMessage="showErrorMessage"
            label="From"
            errorMessage="You have to select start date."
            type="date"
            id="startValidDate"
          />
        </div>
        <div class="col-6">
          <DateTimePicker
            v-model="certificate.endDate"
            :isValid="!!certificate.endDate"
            :showErrorMessage="showErrorMessage"
            label="To"
            errorMessage="You have to select end date."
            type="date"
            id="endValidDate"
          />
        </div>
      </form-row>
      <form-row>
        <SelectOptionInput
          class="col-6"
          :label="'Select standard template'"
          :showLabel="true"
          :options="templates"
          v-model="currentTemplate"
        />

        <MultiSelectOptionInput
          class="col-6"
          label="Add extensions"
          :showLabel="false"
          :options="extensions"
          v-model="checkedExtensions"
        />
      </form-row>
    </form-group>

    <form-group v-if="keyUsageExtension.display">
      <form-row class="col-12">
        <key-usage-form
          :defaultChecked="keyUsageExtension.defaultChecked"
          @addedKey="setKey"
        ></key-usage-form>
      </form-row>
    </form-group>

    <form-group v-if="extendedKeyUsages.display">
      <form-row class="col-12">
        <extended-key-usages-form
          :defaultChecked="extendedKeyUsages.defaultChecked"
          @addedExtendedKey="setExtendedKey"
        ></extended-key-usages-form>
      </form-row>
    </form-group>

    <form-group v-if="authorityKeyIdentifier.display">
      <form-row>
        <label class="bmd-label-floating" style="margin-left: 3%"
          >Authority Key Identifier</label
        >
      </form-row>
      <form-row class="col-12">
        <general-name-table
          :addedOptions="authorityKeyIdentifier.addedOptions"
          modalBoxId="authorityKeyIdentifier"
        />
      </form-row>
    </form-group>

    <form-group v-if="subjectAlternativeNamese.display">
      <form-row>
        <label class="bmd-label-floating" style="margin-left: 3%"
          >Subject Alternative Name</label
        >
      </form-row>
      <form-row class="col-12">
        <general-name-table
          :addedOptions="subjectAlternativeNamese.addedOptions"
          modalBoxId="subjectAlternativeNames"
        />
      </form-row>
    </form-group>

    <form-group v-if="issuerAlternativeNames.display">
      <form-row>
        <label class="bmd-label-floating" style="margin-left: 3%"
          >Issuer Alternative Name</label
        >
      </form-row>
      <form-row class="col-12">
        <general-name-table
          :addedOptions="issuerAlternativeNames.addedOptions"
          modalBoxId="issuerAlternativeNames"
        />
      </form-row>
    </form-group>

    <form-group v-if="nameConstraint.display">
      <form-row>
        <label class="bmd-label-floating" style="margin-left: 3%"
          >Name Constraint</label
        >
      </form-row>
      <form-row class="col-12">
        <NameConstraint
          :addedOptions="nameConstraint.addedOptions"
          modalBoxId="nameConstraint"
        />
      </form-row>
    </form-group>

    <form-group v-if="policyConstraint.display">
      <form-row>
        <label class="bmd-label-floating" style="margin-left: 3%"
          >Policy Constraint</label
        >
      </form-row>
      <form-row class="col-12">
        <PolicyConstraint
          :addedOptions="policyConstraint.addedOptions"
          modalBoxId="policyConstraint"
          @addPolicyConstraint="setPolicy"
        />
      </form-row>
    </form-group>
    <div style="display: flex; justify-content: center">
      <Button @click="showErrorMessage = true" type="submit">Create</Button>
    </div>
  </Form>
</template>

<script>
import Button from "../../generic-components/Form/Button.vue";
import Form from "../../generic-components/Form/Form.vue";
import FormRow from "../../generic-components/Form/FormRow.vue";
import DateTimePicker from "../../generic-components/Form/DateTimePicker.vue";
import { mapActions, mapGetters } from "vuex";
import FormGroup from "../../generic-components/Form/FormGroup.vue";
import KeyUsageForm from "./KeyUsageForm.vue";
import ExtendedKeyUsagesForm from "./ExtendedKeyUsagesForm.vue";
import GeneralNameTable from "../Tables/GeneralNameTable.vue";
import SelectOptionInput from "../../generic-components/Form/SelectOptionInput.vue";
import MultiSelectOptionInput from "../../generic-components/Form/MultiSelectOptionInput.vue";
import toastr from "toastr";
import PolicyConstraint from "../Tables/PolicyConstraintTable.vue";
import NameConstraint from "../Tables/NameConstraintTable.vue";

const $ = window.$;

export default {
  components: {
    Form,
    FormRow,
    Button,
    DateTimePicker,
    FormGroup,
    KeyUsageForm,
    ExtendedKeyUsagesForm,
    GeneralNameTable,
    SelectOptionInput,
    MultiSelectOptionInput,
    PolicyConstraint,
    NameConstraint,
  },
  props: {
    csrId: null,
  },

  data: function () {
    return {
      certificate: {
        startDate: "",
        endDate: "",
      },

      showErrorMessage: false,
      keyUsageExtension: {
        display: false,
        defaultChecked: [],
      },
      extendedKeyUsages: {
        display: false,
        defaultChecked: [],
      },
      authorityKeyIdentifier: {
        display: false,
        addedOptions: [],
      },
      templates: [
        {
          label: "SSL Server",
          value: 0,
        },
        {
          label: "SSL Client",
          value: 1,
        },
        {
          label: "Code Signing",
          value: 2,
        },
      ],
      currentTemplate: null,
      extensions: [
        {
          label: "Policy Constraints",
          value: 0,
        },
        {
          label: "Name Constraints",
          value: 1,
        },
        {
          label: "Issuer Alternative Name",
          value: 2,
        },
      ],
      checkedExtensions: [],
      subjectAlternativeNamese: {
        display: false,
        addedOptions: [],
      },
      issuerAlternativeNames: {
        display: false,
        addedOptions: [],
      },
      nameConstraint: {
        display: false,
        addedOptions: [],
      },
      policyConstraint: {
        display: false,
        addedOptions: [],
      },
    };
  },

  computed: {
    ...mapGetters({
      result: "csr/getResult",
    }),
  },
  watch: {
    currentTemplate(option) {
      this.currentTemplate = option;
      if (this.currentTemplate === 0) {
        this.sslServerTemplate();
      } else if (this.currentTemplate === 1) {
        this.sslClientTemplate();
      } else if (this.currentTemplate === 2) {
        this.codeSigningTemplate();
      } else {
        this.removeAllExtensions();
      }
      setTimeout(() => {
        $(".selectpicker").selectpicker("refresh");
      }, 100);
    },
    checkedExtensions(checked) {
      this.checkedExtensions = checked;
      this.checkedExtensions.forEach((option) => {
        if (option === 0) {
          this.policyConstraint.display = true;
        }
        if (option === 1) {
          this.nameConstraint.display = true;
        }
        if (option === 2) {
          this.issuerAlternativeNames.display = true;
        }
      });
      setTimeout(() => {
        $(".selectpicker").selectpicker("refresh");
      }, 100);
    },
    result({ message, ok, label }) {
      if (label === "accept") {
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
      createCertificate: "csr/acceptCsr",
    }),
    sslServerTemplate() {
      this.extendedKeyUsages.display = true;
      this.extendedKeyUsages.defaultChecked = ["1.3.6.1.5.5.7.3.1"];
      this.keyUsageExtension.display = true;
      this.keyUsageExtension.defaultChecked = [128, 32];
      this.authorityKeyIdentifier.display = true;
      this.subjectAlternativeNamese.display = true;
    },
    sslClientTemplate() {
      this.extendedKeyUsages.display = true;
      this.extendedKeyUsages.defaultChecked = ["1.3.6.1.5.5.7.3.2"];
      this.keyUsageExtension.display = true;
      this.keyUsageExtension.defaultChecked = [32];
      this.authorityKeyIdentifier.display = true;
      this.subjectAlternativeNamese.display = false;
    },
    codeSigningTemplate() {
      this.extendedKeyUsages.display = true;
      this.extendedKeyUsages.defaultChecked = ["1.3.6.1.5.5.7.3.3"];
      this.keyUsageExtension.display = true;
      this.keyUsageExtension.defaultChecked = [128];
      this.authorityKeyIdentifier.display = true;
      this.subjectAlternativeNamese.display = false;
    },
    removeAllExtensions() {
      this.extendedKeyUsages.display = false;
      this.extendedKeyUsages.defaultChecked = [];
      this.keyUsageExtension.display = false;
      this.keyUsageExtension.defaultChecked = [];
      this.authorityKeyIdentifier.display = false;
      this.authorityKeyIdentifier.addedOptions = [];
      this.subjectAlternativeNamese.display = false;
      this.subjectAlternativeNamese.addedOptions = [];
    },
    setExtendedKey(arg) {
      this.extendedKeyUsages.defaultChecked = arg;
    },
    setKey(arg) {
      this.keyUsageExtension.defaultChecked = arg;
    },
    setPolicy(arg) {
      this.policyConstraint.addedOptions = arg;
    },
    onSubmit(e) {
      e.preventDefault();
      const certificate = {
        startDate: this.certificate.startDate,
        endDate: this.certificate.endDate,
      };
      if (
        this.keyUsageExtension.display &&
        this.keyUsageExtension.defaultChecked.length > 0
      ) {
        certificate.keyUsagesExtension = this.keyUsageExtension.defaultChecked;
      }
      if (
        this.extendedKeyUsages.display &&
        this.extendedKeyUsages.defaultChecked.length > 0
      ) {
        certificate.extendedKeyUsages = this.extendedKeyUsages.defaultChecked;
      }
      if (
        this.authorityKeyIdentifier.display &&
        this.authorityKeyIdentifier.addedOptions.length > 0
      ) {
        const map = new Map();
        this.authorityKeyIdentifier.addedOptions.forEach((option) =>
          map.set(option.value, option.enteredValue)
        );
        certificate.generalNamesForAuthorityKeyIdentifier =
          Object.fromEntries(map);
      }
      if (
        this.subjectAlternativeNamese.display &&
        this.subjectAlternativeNamese.addedOptions.length > 0
      ) {
        const map = new Map();
        this.subjectAlternativeNamese.addedOptions.forEach((option) =>
          map.set(option.value, option.enteredValue)
        );
        certificate.subjectAlternativeNames = Object.fromEntries(map);
      }
      if (
        this.issuerAlternativeNames.display &&
        this.issuerAlternativeNames.addedOptions.length > 0
      ) {
        const map = new Map();
        this.issuerAlternativeNames.addedOptions.forEach((option) =>
          map.set(option.value, option.enteredValue)
        );
        certificate.issuerAlternativeNames = Object.fromEntries(map);
      }
      if (
        this.policyConstraint.display &&
        this.policyConstraint.addedOptions.length > 0
      ) {
        certificate.requireExplicitPolicy =
          this.policyConstraint.addedOptions[0];
        certificate.inhabitPolicyMapping =
          this.policyConstraint.addedOptions[1];
      }
      console.log(certificate);
      const csrId = this.csrId;
      this.createCertificate({ csrId, certificate });
    },
  },
  mounted() {},
};
</script>
