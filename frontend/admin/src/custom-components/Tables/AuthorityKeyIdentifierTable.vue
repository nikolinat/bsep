
<template>
    <div class="col-6"> 
        <label class="bmd-label-floating">Authority Key Issuer</label>
        <Table>
            <TableHead :columnNames="['General Name', 'Value', '']"></TableHead>
            <TableBody>
                <TableRow 
                    v-for="(option, i) in addedOptions" 
                    :key="i" 
                    :values="[option.label, option.enteredValue]"
                >
                    <div class="pull-right text-gray">
                        <DropDownMenu>
                            <ModalOpener
                                :modalBoxId="'remove'"
                            >
                                <DropDownItem @click="remove(i)">Remove</DropDownItem>
                            </ModalOpener>
                        </DropDownMenu>
                    </div>
                </TableRow>
            </TableBody>
        </Table>

        <div class="col-6">
            <AddGeneralNameModal modalBoxId="addGeneralName" @newGeneralName="handleAddGeneralNameToAuthorityKeyIdentifier"/>

            <ModalOpener modalBoxId="addGeneralName">
                <Button type="button">Add a General Name</Button>
            </ModalOpener>
        </div>
    </div>
</template>

<script>

import TableHead from '../../generic-components/Table/TableHead.vue'
import Table from '../../generic-components/Table/Table.vue'
import TableBody from '../../generic-components/Table/TableBody.vue'
import TableRow from '../../generic-components/Table/TableRow.vue'
import Button from '../../generic-components/Form/Button.vue'
import AddGeneralNameModal from '../Modals/AddGeneralNameModal.vue'
import ModalOpener from '../../generic-components/Modal/ModalOpener.vue'
import DropDownItem from '../../generic-components/DropdownMenu/DropdownItem.vue'
import DropDownMenu from '../../generic-components/DropdownMenu/DropdownMenu.vue'

export default {
   components: {
       TableHead,
       Table,
       TableBody,
       TableRow,
       Button,
       AddGeneralNameModal,
       ModalOpener,
       DropDownItem,
       DropDownMenu
    },
    props: {
      addedOptions: {
          type: Array,
            default: () => []
      }
    },

    data: function() {
        return {
        }
    },

    computed: {

    },

    watch: {
        addedOptions(options) {
            this.addedOptions = options;
        }
    },

    methods: {
        remove(index) {
            this.addedOptions.splice(index, 1);
        },

        handleAddGeneralNameToAuthorityKeyIdentifier(arg) {
            this.addedOptions.push({
                label: arg.label,
                value: arg.value,
                enteredValue: arg.enteredValue
            })
        }
    }
}

</script>