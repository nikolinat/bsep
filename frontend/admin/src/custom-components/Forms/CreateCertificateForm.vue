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

                <MultiSelectOptionInput class="col-6"
                    label="Add extensions"
                    :showLabel="false"
                    :options="extensions"
                    v-model="checkedExtensions"
                />
            </form-row>
        </form-group>

        <form-group v-if="keyUsageExtension.display">
            <form-row class="col-12">
                <key-usage-form :defaultChecked="keyUsageExtension.defaultChecked" @addedKey="setKey"></key-usage-form>
            </form-row>
        </form-group>

        <form-group v-if="extendedKeyUsages.display"> 
            <form-row class="col-12">
                <extended-key-usages-form :defaultChecked="extendedKeyUsages.defaultChecked" @addedExtendedKey="setExtendedKey"></extended-key-usages-form>
            </form-row>
        </form-group>

        <form-group v-if="authorityKeyIdentifier.display"> 
            <form-row>
                <label class="bmd-label-floating" style="margin-left: 3%">Authority Key Identifier</label>
            </form-row>
            <form-row class="col-12">
                <general-name-table :addedOptions="authorityKeyIdentifier.addedOptions" modalBoxId="authorityKeyIdentifier" />
            </form-row>
        </form-group>

        <form-group v-if="subjectAlternativeNamese.display"> 
            <form-row>
                <label class="bmd-label-floating" style="margin-left: 3%">Subject Alternative Name</label>
            </form-row>
            <form-row class="col-12">
                <general-name-table :addedOptions="subjectAlternativeNamese.addedOptions" modalBoxId="subjectAlternativeNames" />
            </form-row>
        </form-group>

        <form-group v-if="issuerAlternativeNames.display"> 
            <form-row>
                <label class="bmd-label-floating" style="margin-left: 3%">Issuer Alternative Name</label>
            </form-row>
            <form-row class="col-12">
                <general-name-table :addedOptions="issuerAlternativeNames.addedOptions" modalBoxId="issuerAlternativeNames" />
            </form-row>
        </form-group>
        
        <div style="display: flex; justify-content: center;">
            <Button @click="showErrorMessage = true" type="submit">Create</Button>
        </div>
    </Form>
</template>

<script>

import Button from '../../generic-components/Form/Button.vue'
import Form from '../../generic-components/Form/Form.vue'
import FormRow from '../../generic-components/Form/FormRow.vue'
import DateTimePicker from '../../generic-components/Form/DateTimePicker.vue'
import { mapActions } from 'vuex'
import FormGroup from '../../generic-components/Form/FormGroup.vue'
import KeyUsageForm from './KeyUsageForm.vue'
import ExtendedKeyUsagesForm from './ExtendedKeyUsagesForm.vue'
import GeneralNameTable from '../Tables/GeneralNameTable.vue'
import SelectOptionInput from '../../generic-components/Form/SelectOptionInput.vue'
import MultiSelectOptionInput from '../../generic-components/Form/MultiSelectOptionInput.vue'

// const $ = window.$;

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
        MultiSelectOptionInput
    },

    data: function() {
        return {
            certificate: {
                startDate: '',
                endDate: '',
            },
        
            showErrorMessage: false,
            keyUsageExtension: {
                display: false,
                defaultChecked: []
            },
            extendedKeyUsages: {
                display: false,
                defaultChecked: []
            },
            authorityKeyIdentifier: {
                display: false,
                addedOptions: []
            },
            templates: [
              {
                  label: "SSL Server",
                  value: 0
              },
              {
                  label: "SSL Client",
                  value: 1
              },
              {
                  label: "Code Signing",
                  value: 2
              },
            ],
            currentTemplate: null,
            extensions: [
                {
                    label: "Policy Constraints",
                    value: 0
                },
                {
                    label: "Name Constraints",
                    value: 1
                },
                {
                    label: "Issuer Alternative Name",
                    value: 2
                }
            ],
            checkedExtensions: [],
            subjectAlternativeNamese: {
                display: false,
                addedOptions: []
            },
            issuerAlternativeNames: {
                display: false,
                addedOptions: []
            }
        }
    },

    computed: {

    },

    watch: {
        currentTemplate(option) {
            this.currentTemplate = option;
            if(this.currentTemplate === 0) {
                this.sslServerTemplate();
            }
            else if(this.currentTemplate === 1) {
                this.sslClientTemplate()
            }
            else if(this.currentTemplate === 2) {
                this.codeSigningTemplate()
            }
            else {
                this.removeAllExtensions()
            }  
        },
        checkedExtensions(checked) {
            this.checkedExtensions = checked;
            this.checkedExtensions.forEach(option => {
                if(option === 2) {
                    this.issuerAlternativeNames.display = true;
                }
            })
        }
    },

    methods: {
        ...mapActions({ 

        }),

        onSubmit(e) {
            e.preventDefault();
        },

        sslServerTemplate() {
            this.extendedKeyUsages.display = true;
            this.extendedKeyUsages.defaultChecked = ["1.3.6.1.5.5.7.3.1"]
            this.keyUsageExtension.display = true;
            this.keyUsageExtension.defaultChecked = [128, 32]
            this.authorityKeyIdentifier.display = true;
            this.subjectAlternativeNamese.display = true;
        },

        sslClientTemplate() {
            this.extendedKeyUsages.display = true;
            this.extendedKeyUsages.defaultChecked = ["1.3.6.1.5.5.7.3.2"]
            this.keyUsageExtension.display = true;
            this.keyUsageExtension.defaultChecked = [32]
            this.authorityKeyIdentifier.display = true;
            this.subjectAlternativeNamese.display = false;
        },

        codeSigningTemplate() {
            this.extendedKeyUsages.display = true;
            this.extendedKeyUsages.defaultChecked = ["1.3.6.1.5.5.7.3.3"]
            this.keyUsageExtension.display = true;
            this.keyUsageExtension.defaultChecked = [128]
            this.authorityKeyIdentifier.display = true;
            this.subjectAlternativeNamese.display = false;
        },

        removeAllExtensions() {
            this.extendedKeyUsages.display = false;
            this.extendedKeyUsages.defaultChecked = []
            this.keyUsageExtension.display = false;
            this.keyUsageExtension.defaultChecked = []
            this.authorityKeyIdentifier.display = false;
            this.authorityKeyIdentifier.addedOptions = []
            this.subjectAlternativeNamese.display = false;
            this.subjectAlternativeNamese.addedOptions = []
        },

        setExtendedKey(arg) {
            this.extendedKeyUsages.defaultChecked = arg;
        },

        setKey(arg) {
            this.keyUsageExtension.defaultChecked = arg;
        }

    },

    mounted() {
    }
}

</script>
