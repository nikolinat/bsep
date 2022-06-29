<template>
    <div class="content">
        <RealEstateCards :realEstates="realEstates" :isOwner="true"/>
    </div>
</template>

<script>

import { mapGetters, mapActions } from 'vuex'
import RealEstateCards from "../custom-components/Cards/RealEstateCards.vue"

export default {
    components: {
        RealEstateCards
    },
    name: 'RealEstates',

     data: () => {
        return {
            realEstates: [],
        }
    },
    computed: {
        ...mapGetters({
            getRealEstates: 'realestate/getRealEstates',
        })
    },
    watch: {
        getRealEstates(realEstates) {
            this.realEstates = realEstates;
        }
    },
    methods: {
        ...mapActions({
            fetchRealEstates: 'realestate/fetchRealEstatesByOwner',
        })
    },
    mounted() {
        this.fetchRealEstates();
    }
}
</script>
