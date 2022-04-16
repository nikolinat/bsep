<template>
    <div> 
        <Table>
            <TableHead :columnNames="['Entry Name', 'Certificate Expiry', '']"></TableHead>
            <TableBody>
                <TableRow 
                    v-for="(certificate, i) in certificates" 
                    :key="i" 
                    :values="[certificate.serialNumber,certificate.endDate]"
                >
                    <div class="pull-right text-gray">
                        <DropDownMenu>
                            <ModalOpener
                                :modalBoxId="'revokeCertificate'"
                            >
                                <DropDownItem @click="selectedCertificate = certificate">Revoke certificate</DropDownItem>
                            </ModalOpener>
                        </DropDownMenu>
                    </div>
                </TableRow>
            </TableBody>
        </Table>

        <Modal
            modalBoxId="revokeCertificate"
            title="Revoke certificate"
        >
            <div slot="body">
                <p v-if="selectedCertificate">Are you sure that you want to revoke certificate {{selectedCertificate.serialNumber}}</p>
            </div>

            <div slot="buttons">
                <OptionModalButtons @yes="onRemoveSubmit"/>
            </div>
        </Modal>
    </div>
</template>

<script>

</script>