<template>
    <Form>
        <form-row>
            <SelectOptionInput
                class="col-6"
                label="Select log type"
                :showLabel="false"
                :options="logTypes"
                v-model="logSearchDto.logType"
            />
        </form-row>

        <form-row>
            <div class="col-6">
                <text-input 
                    label="Message or message regex"
                    v-model="logSearchDto.messageRegex"
                    type="text"
                />
            </div>
            <DateTimePicker class="col-6"
                v-model="logSearchDto.dateTime"
                label="Date"
                type="date"
                id="dateSearch"
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
import toastr from 'toastr';
import moment from 'moment';

export default {
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
            logTypes: [
                {
                label: "ERROR",
                value: "ERROR",
                },
                {
                label: "WARNING",
                value: "WARNING",
                },
                {
                label: "SUCCESS",
                value: "SUCCESS",
                },
            ],
            logSearchDto: {
                logType: null,
                messageRegex: "",
                dateTime: moment()
            }
        }
    },

    computed: {
        ...mapGetters({
            result: "log/getResult"
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
            searchAndFilterLogs: "log/searchAndFilterLogs",
        }),

        handleSearchAndFilter() {
            this.logSearchDto.dateTime = moment(this.logSearchDto.dateTime).format(moment.HTML5_FMT.DATETIME_LOCAL_SECONDS);
            this.searchAndFilterLogs(this.logSearchDto);
        }
    }
}

</script>
