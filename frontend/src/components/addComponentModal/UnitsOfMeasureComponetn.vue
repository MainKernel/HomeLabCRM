<template>
    <div class="input-field-container">
        <label>Одиниці вимірювання</label>
        <div class="input-wrapper">
            <select v-model="formData.unitsOfMeasure" id="1" >
                <option value="PCS">ШТУК</option>
                <option value="PACKS">ПАЧОК</option>
                <option value="KG">КІЛОГРАМ</option>
                <option value="GRAMS">ШТУК</option>
                <option value="METERS">МЕТРІВ</option>
                <option value="UDEF">НЕВИЗНАЧЕНО</option>
            </select>
        </div>
    </div>
</template>
<script setup>
import { useModalStore } from './modalStore';
import { storeToRefs } from 'pinia';

const useStore = useModalStore ()
const {formData} = storeToRefs(useStore)

</script>
<style scoped>
.input-field-container{
    display: flex;
    flex-direction: column;
}
label{
    padding-left: 10px;
    margin-right: 5px;
    color: whitesmoke;
    font-weight: 900;
}
select{
    margin-top: 5px;
    padding-right: 5px;
    margin-left: 5px;
    margin-right: 5px;
    color: whitesmoke;
    background-color: var(--color-surface, #1e1e1e);
    border: 2px solid #9c50ff8d;
    min-height: 25px;
    padding-left: 5px;
    border-radius: 5px;
    width: 100%;
    box-sizing: border-box;
    
}
.input-wrapper{
    margin-top: 5px;
    margin-left: 5px;
    margin-right: 5px;
    padding-right: 5px;
}
select:focus{
    outline: none;
    box-shadow: 0 0 5px rgba(0, 255, 65, 0.5), 
              0 0 15px rgba(0, 255, 65, 0.4);
}
</style>