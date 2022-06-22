<template>
  <div class="content">
    <div class="container-fluid">
      <div class="center align-items-center col-6" style="margin-left: 10%">
        <card :title="'Add new rule'">
          <AddRuleForm />
        </card>
      </div>
    </div>
  </div>
</template>

<script>
import Card from "../generic-components/Card/Card.vue";
import AddRuleForm from "../custom-components/Forms/AddRuleForm.vue";

export default {
  components: {
    Card,
    AddRuleForm,
  },
  name: "RulePage",
};
</script>
