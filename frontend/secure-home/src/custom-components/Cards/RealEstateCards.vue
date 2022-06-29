<template>
    <div class="content">
            <div class="row">
                <template v-for="(realEstate, index) in realEstates">
                    <RotatingCard :key="index" :name="realEstate.name" :address="realEstate.address">
                        <div slot="buttons">
                            <ModalOpener :modalBoxId="'ownersModal'">
                                <Button @click="handleViewOwneres(realEstate.owners)">Owners</Button>
                            </ModalOpener>
                            <ModalOpener :modalBoxId="'tenantsModal'">
                                <Button @click="handleViewTenants(realEstate.tenants)">Tenants</Button>
                            </ModalOpener>
                            <ModalOpener :modalBoxId="'devicesModal'">
                                <Button @click="handleViewDevices(realEstate.id)">Devices</Button>
                            </ModalOpener>
                            <ModalOpener :modalBoxId="'devicesMessagesModal'">
                                <Button @click="handleViewDevicesMessages(realEstate.id)">Messages</Button>
                            </ModalOpener>
                             <ModalOpener v-if="roles.includes('ROLE_HOUSE_OWNER') && isOwner === true" :modalBoxId="'createAlarmModal'">
                                <Button @click="handleCreateAlarm(realEstate.id)">Add alarm</Button>
                            </ModalOpener>
                            <ModalOpener :modalBoxId="'alarmsModal'">
                                <Button @click="handleViewAlarms(realEstate.id)">Alarms</Button>
                            </ModalOpener>
                            <ModalOpener :modalBoxId="'reportModal'">
                                <Button @click="handleViewReport(realEstate.id)">Report</Button>
                            </ModalOpener>
                        </div>
                    </RotatingCard>
                </template>
            </div>

        <Modal modalBoxId="ownersModal" :title="'Owners'" sizeClass="modal-lg">
            <div slot="body">
                <UsersTable :users="owners" />
            </div>
        </Modal>

        <Modal modalBoxId="tenantsModal" :title="'Tenants'" sizeClass="modal-lg">
            <div slot="body">
                <UsersTable :users="tenants" />
            </div>
        </Modal>

        <Modal modalBoxId="devicesModal" :title="'Devices'" sizeClass="modal-lg">
            <div slot="body">
                <DevicesTable :devices="devices" />
            </div>
        </Modal>

        <Modal modalBoxId="devicesMessagesModal" :title="'Device messages'" sizeClass="modal-lg">
            <div slot="body">
                <SearchFilterDevicesMessages  :realEstateId="realEstateId"/>
                <DeviceMessageTable :devicesMessages="devicesMessages" />
            </div>
        </Modal>

        <Modal modalBoxId="alarmsModal" :title="'Alarms'" sizeClass="modal-lg">
            <div slot="body">
                <AlarmsTable :alarms="alarms" />
            </div>
        </Modal>
        <Modal  v-if="realEstateId !== null" modalBoxId="createAlarmModal" :title="'Create alarm'" sizeClass="modal-sg">
            <div slot="body">
                <AddAlarmForm :realEstateId="realEstateId" />
            </div>
        </Modal>
         <Modal v-if="realEstateId !== null" modalBoxId="reportModal" :title="'Report'" sizeClass="modal-lg">
            <div slot="body">
                <card>
                    <Report :realEstateId="realEstateId" />
                </card>
            <card>
                <ReportTable />
            </card>
            </div>
          
        </Modal>

    </div>
</template>

<script>

import RotatingCard from "../../generic-components/Card/RotatingCard.vue"
import Button from "../../generic-components/Form/Button.vue"
import ModalOpener from "../../generic-components/Modal/ModalOpener.vue"
import Modal from "../../generic-components/Modal/Modal.vue"
import UsersTable from "../Tables/UsersTable.vue"
import DevicesTable from '../Tables/DevicesTable.vue'
import DeviceMessageTable from '../Tables/DeviceMessageTable.vue'
import SearchFilterDevicesMessages from '../Forms/SearchFilterDevicesMessages.vue'
import AlarmsTable from '../Tables/AlarmsTable.vue'
import AddAlarmForm from '../Forms/AddAlarmForm.vue'
import { getRoleFromToken } from '../../utils/token.js'
import Report from "../Forms/ReportForm.vue"
import Card from '../../generic-components/Card/Card.vue'
import ReportTable from '../../custom-components/Tables/ReportTable.vue'

import { mapActions, mapGetters } from 'vuex'

export default {
    props: ['realEstates', 'isOwner'],
    components: {
        RotatingCard,
        Button,
        ModalOpener,
        Modal,
        UsersTable,
        DevicesTable,
        DeviceMessageTable,
        SearchFilterDevicesMessages,
        AlarmsTable,
        AddAlarmForm,
        Report,
        Card,
        ReportTable
    },
    name: 'RealEstates',

     data: () => {
        return {
            owners: [],
            tenants: [],
            realEstateId: null,
            roles: []
        }
    },
    computed: {
        ...mapGetters({
            devices: 'devices/getDevices',
            devicesMessages: 'deviceMessage/getDevicesMessages',
            alarms: 'alarms/getAlarms'
        })
    },
    methods: {
        ...mapActions({
            fetchDevicesByRealEstate: 'devices/fetchDevicesForRealEstate',
            fetchDevicesMessagesForRealEstate: 'deviceMessage/fetchDevicesMessagesForRealEstate',
            fetchAlarmsByRealEstate: 'alarms/fetchAlarmsForRealEstate',

        }),

        handleViewOwneres(owners) {
            this.owners = owners;
        },

        handleViewTenants(tenants) {
            this.tenants = tenants;
        },

        handleViewDevices(id) {
            this.fetchDevicesByRealEstate(id);
        },

        handleViewDevicesMessages(id) {
            this.realEstateId = id;
            this.fetchDevicesMessagesForRealEstate(id);
        },

        handleViewAlarms(id) {
            this.fetchAlarmsByRealEstate(id);
        },

        handleCreateAlarm(id){
            this.realEstateId = id;
        },
        handleViewReport(id){
            this.realEstateId = id;
        }
    },
    mounted() {
        this.roles = getRoleFromToken();
    }
}
</script>
