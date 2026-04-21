<template>
    <div class="page-container">
    <div class="pagination-choicer-container">
        <select id="perPage" :value="itemsPerPage" @change="onSizeChange">
            <option :value="10">10</option>
            <option :value="15">15</option>
            <option :value="20">20</option>
            <option :value="30">30</option>
            <option :value="50">50</option>
        </select>
    </div>
    <div class="pagination-container">
        <button class="page-btn" 
        :disabled= "currentPage === 0"
        @click="onPageClick(currentPage - 1)">
       &lt;
        </button>
    
     <span class="page-info"> {{ currentPage + 1 }} з {{ totalPages }}</span>
    
        <button class="page-btn" 
        :disabled="currentPage >= totalPages - 1"
        @click="onPageClick(currentPage + 1)">
         &gt;
        </button>
    </div>
</div>
</template>
<script setup>

const props = defineProps({
    currentPage: {
        type: Number,
        required: true
    },
    totalPages: {
        type: Number,
        required: true
    },
    itemsPerPage: {
        type: Number,
        required: true
    }
})

const emit = defineEmits(['change-page', 'size-change'])

const onPageClick = (newPage) => {    
    if (newPage >= 0 && newPage < props.totalPages) {
        emit('change-page', newPage)
    }
}

const onSizeChange = (event) => {
    const newSize = Number(event.target.value); 
    emit('size-change', newSize);
}

</script>
<style scoped>
.page-container{
    display: grid;
    grid-template-columns: 4fr 20fr;
}
.pagination-choicer-container{
    display: flex;
    margin-top: 10px;
    justify-content: flex-start;
    padding-left: 20px;
    justify-items: center;
    
}
.pagination-container{
    display: flex;
    justify-content: center;
    justify-items: center;
    margin-top: 10px;
    padding-right: 200px;
}
span{
    margin-left: 10px;
    margin-right: 10px;
    text-align: center;
    align-self: center;
}
.page-btn{
    background-color: rgba(97,71,176, 1.0);
    color: whitesmoke;
    border: 0px;
    height: 2rem;
    width: 2rem;
    border-radius: 5px;
    box-shadow: 1px 2px 2px gray;
}
.page-btn:hover{
    border: 2px solid black; 
    box-shadow: none;

}
.page-btn:disabled{
    background-color: azure;
    color: gray;
    
}
select{
    background-color: var(--color-surface, #1e1e1e);
    border-radius: 5px;
    border: none;
}
</style>