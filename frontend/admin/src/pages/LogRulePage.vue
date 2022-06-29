<template>
  <div class="content">
    <div class="container-fluid">
      <div class="center align-items-center col-6" style="margin-left: 10%">
        <card :title="'Add new log rule'">
          <AddLogRuleForm />
        </card>
      </div>
    </div>
  </div>
</template>

<script>
import Card from "../generic-components/Card/Card.vue";
import AddLogRuleForm from "../custom-components/Forms/AddLogRule.vue"

export default {
  components: {
    Card,
    AddLogRuleForm,
  },
  name: "LogRulePage",
};
</script>
