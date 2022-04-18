<template>
  <div class="content">
    <div class="container-fluid">
      <Card title="Name Constraint Extension">
        <Card title="Permitted Subtrees">
          <NameConstraintForm
            :subtrees="permittedSubtrees"
          ></NameConstraintForm>
        </Card>
        <Card title="Excluded Subtrees">
          <NameConstraintForm :subtrees="excludedSubtrees"></NameConstraintForm>
        </Card>
      </Card>
    </div>
  </div>
</template>
<script>
import NameConstraintForm from "../../custom-components/Forms/NameConstraintSubtreesForm.vue";
import Card from "../../generic-components/Card/Card.vue";
export default {
  permittedSubtrees: null,
  excludedSubtrees: null,

  components: { NameConstraintForm, Card },
};
</script>
