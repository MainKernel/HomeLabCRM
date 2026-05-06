<template>
  <button>
    <img alt="Редагувати"
         height="25px"
         src="../../public/edit-button.svg"
         width="25px"
         @click.prevent="goToItem(itemId)"
    />
  </button>
</template>
<script setup>
import {useRouter} from "vue-router";

defineProps({
  itemId: Number
})

const router = useRouter();

const goToItem = (itemId) => {
  router.push(`inventory/item/${itemId}`);
}

</script>
<style scoped>
button {
  background-color: transparent;
  border: none;
  margin: 0;
  padding: 0;
}

img:hover {
  border: 3px dashed black;
  border-radius: 5px;
}
</style>