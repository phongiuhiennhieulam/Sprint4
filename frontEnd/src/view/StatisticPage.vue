<template>
    <div class="static-container">
        <div class="bar">
            <Bar :chart-options="chartOptions" :chart-data="chartData" :chart-id="chartId"
                :dataset-id-key="datasetIdKey" :plugins="plugins" :css-classes="cssClasses" :styles="styles" />
        </div>
    </div>
</template>

<script>
import { Bar } from 'vue-chartjs/legacy'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)
import axios from 'axios'
export default {
    components: { Bar },
    props: {
        chartId: {
            type: String,
            default: 'bar-chart'
        },
        datasetIdKey: {
            type: String,
            default: 'label'
        },

        cssClasses: {
            default: '',
            type: String
        },


    },
    data() {
        return {
            data: [],
            chartOptions: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                        
                    }
                }
            },

        }
    },
    computed: {
        styles() {
            return {
                height: `98%`
            }
        },

        chartData() {
            return {
                labels: this.data.map(x => x.name),
                datasets: [
                    {
                        backgroundColor: '#f87979',
                        data: this.data.map(x => x.quantity)
                    },
                ],


            }
        }
    },
    async created() {
        const res = await axios.get('http://localhost:8083/api/getStatisticWelfare')
        if (res.status == 200) {

            res.data.forEach(element => {
                let obj = {
                    name: element.name,
                    quantity: element.quantity
                }
                this.data.push(obj);
            });

        }
    }

}
</script>

<style scoped>
.static-container {
    display: flex;
    height: 100%;
}

.bar {
    width: 100%;
    height: 100%;
}
</style>