<template>
    <div class="content">
        <div class="container-fluid">
            <div class="center align-items-center col-10" style="margin-left: 10%">
                <card :title="'Create certificate'">
                    <CreateCertificateForm />
                </card>
            </div>
        </div>
    </div>
</template>

<script>
import Card from '../generic-components/Card/Card.vue'
import CreateCertificateForm from '../custom-components/Forms/CreateCertificateForm.vue'

export default {
    components: {
        Card,
        CreateCertificateForm
    },
    name: 'CreateCertificatePage',
}
</script>
