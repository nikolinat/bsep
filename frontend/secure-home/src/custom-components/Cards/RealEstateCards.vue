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
                            <Button>Messages</Button>
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
    </div>
</template>

<script>

import RotatingCard from "../../generic-components/Card/RotatingCard.vue"
import Button from "../../generic-components/Form/Button.vue"
import ModalOpener from "../../generic-components/Modal/ModalOpener.vue"
import Modal from "../../generic-components/Modal/Modal.vue"
import UsersTable from "../Tables/UsersTable.vue"
import DevicesTable from '../Tables/DevicesTable.vue'
import { mapActions, mapGetters } from 'vuex'

export default {
    props: ['realEstates'],
    components: {
        RotatingCard,
        Button,
        ModalOpener,
        Modal,
        UsersTable,
        DevicesTable
    },
    name: 'RealEstates',

     data: () => {
        return {
            owners: [],
            tenants: [],
        }
    },
    computed: {
        ...mapGetters({
            devices: 'devices/getDevices'
        })
    },
    methods: {
        ...mapActions({
            fetchDevicesByRealEstate: 'devices/fetchDevicesForRealEstate'
        }),

        handleViewOwneres(owners) {
            this.owners = owners;
        },

        handleViewTenants(tenants) {
            this.tenants = tenants;
        },

        handleViewDevices(id) {
            this.fetchDevicesByRealEstate(id);
        }
    }
}
</script>
