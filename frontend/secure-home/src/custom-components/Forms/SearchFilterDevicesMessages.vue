<template>
    <Form>
        <form-row>
            <div class="col-6">
                <text-input 
                    label="Message"
                    v-model="searchFilterDto.message"
                    type="text"
                />
            </div>
            <DateTimePicker class="col-6"
                v-model="searchFilterDto.dateTime"
                label="Date"
                type="date"
                id="dateSearch"
          />
        </form-row>

        <form-row>
            <SelectOptionInput
                class="col-6"
                label="Is alarm"
                :showLabel="false"
                :options="alarms"
                v-model="searchFilterDto.alarm"
            />
            <SelectOptionInput
                class="col-6"
                label="Select device type"
                :showLabel="false"
                :options="deviceTypes"
                v-model="searchFilterDto.type"
            />
        </form-row>

        <Button @click="handleSearchAndFilter">Search</Button>
    </Form>
</template>

<script>

import Button from '../../generic-components/Form/Button.vue'
import Form from '../../generic-components/Form/Form.vue'
import FormRow from '../../generic-components/Form/FormRow.vue'
import TextInput from '../../generic-components/Form/TextInput.vue'
import SelectOptionInput from '../../generic-components/Form/SelectOptionInput.vue'
import DateTimePicker from '../../generic-components/Form/DateTimePicker.vue'
import { mapGetters, mapActions } from "vuex";
import { types } from '../../constants.js'
import toastr from 'toastr';
import moment from 'moment'

export default {
    props: ['realEstateId'],
   components: {
       Form,
       FormRow,
       TextInput,
       Button,
       SelectOptionInput,
       DateTimePicker
    },

    data: function() {
        return {
            alarms: [
                {
                label: "Alarm",
                value: true,
                },
                {
                label: "Not alarm",
                value: false,
                },
            ],
            deviceTypes: types,
            searchFilterDto: {
                message: "",
                dateTime: moment(),
                alarm: null, 
                type: null
            }
        }
    },

    computed: {
        ...mapGetters({
            result: "deviceMessage/getResult"
            }),
    },

    watch: {
        result({ message, ok, label }) {
        if (label === "fetch") {
            if (!ok) {
                toastr.error(message);
            }
        }
        }
    },

    methods: {
        ...mapActions({
            filterSearchDevicesMessages: "deviceMessage/filterSearchDevicesMessages",
        }),

        handleSearchAndFilter() {
            this.searchFilterDto.dateTime = moment(this.searchFilterDto.dateTime).format(moment.HTML5_FMT.DATETIME_LOCAL_SECONDS);
            this.filterSearchDevicesMessages({
                realestateId: this.realEstateId,
                searchFilterDeviceMessagesDto: this.searchFilterDto
        });
        }
    },

    mounted() {
    }
}

</script>
