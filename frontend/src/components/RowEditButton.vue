<template>
    <button>
        <img src="../../public/edit-button.svg" 
        alt="Редагувати" 
        height="25px" 
        width="25px"
        @click="openEditModal = true"
        />
    </button>
</template>
<script setup>
defineProps({
  item: Number  
})

</script>
<style scoped>
button{
    background-color: transparent;
    border: none;
    margin: 0px;
    padding: 0px;
}
</style>