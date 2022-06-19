<template>
    <div class="col-md-3 ml-auto mr-auto">
        <div class="rotating-card-container">
            <div class="card card-rotate card-background">
                <div class="front front-background" style="background-image: url('https://assets.kpmg/is/image/kpmg/purple-and-punk-lines-connected-to-white-light-dots-blue-background:cq5dam.web.512.341'); color: white;">
                    <div class="card-body">
                        <h4 class="card-category">{{name}}</h4>
                        <h2 class="card-title">{{address}}</h2>
                        <slot name="description" />
                    </div>
                </div>

                <div class="back back-background" style="background-image: url('https://assets.kpmg/is/image/kpmg/purple-and-punk-lines-connected-to-white-light-dots-blue-background:cq5dam.web.512.341'); color: white;">
                    <div class="card-body">
                        <h5 class="card-title">
                            {{label}}
                        </h5>
                        <div class="footer justify-content-center">
                            <slot name="buttons"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
  props: ['name', 'address', 'label']
}
</script>

<style>

</style>