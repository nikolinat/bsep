<template>
  <div class="col-12">
    <Card title="Permitted Subtrees">
      <NameConstraintForm
        :subtrees="addedOptions"
        modalBoxId="modalBoxId"
      ></NameConstraintForm>
    </Card>
    <Card title="Excluded Subtrees">
      <NameConstraintForm
        :subtrees="addedOptions"
        modalBoxId="modalBoxId"
      ></NameConstraintForm>
    </Card>
  </div>
</template>
<script>
import NameConstraintForm from "../../custom-components/Forms/NameConstraintSubtreesForm.vue";
import Card from "../../generic-components/Card/Card.vue";
export default {
  components: { NameConstraintForm, Card },
  props: {
    addedOptions: {
      type: Array,
      default: () => [],
    },
    modalBoxId: {
      type: String,
    },
  },
};
</script>
