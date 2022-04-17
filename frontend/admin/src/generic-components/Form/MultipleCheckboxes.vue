<template>
    <div class="form-check" style="display: flex; flex-wrap: wrap; margin-left: 2%">
        <div v-for="(option, index) in options" :key="index" style="height: 3em">
            <label class="form-check-label">
                <input class="form-check-input" type="checkbox" :value="option.value" v-model="options"> {{ option.name }}
                <span class="form-check-sign">
                    <span class="check"></span>
                </span>
            </label>
        </div>
    </div>
</template>

<script>

export default {
  components: {
  },
  props: {
    options: {
      default: []
    }
  },
  data: {
    checkedNames: []
  },
  computed: {
      
  }
}
</script>

<style>

</style>