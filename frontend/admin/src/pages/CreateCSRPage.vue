<template>
    <div class="content">
        <div class="container-fluid">
            <div class="center align-items-center col-10" style="margin-left: 10%">
                <card :title="'Create CSR'">
                    <CreateCSRForm />
                </card>
            </div>
        </div>
    </div>
</template>

<script>
import Card from '../generic-components/Card/Card.vue'
import CreateCSRForm from '../custom-components/Forms/CreateCSRForm.vue'

export default {
    components: {
        Card,
        CreateCSRForm
    },
    name: 'CreateCsrPage',
}
</script>
