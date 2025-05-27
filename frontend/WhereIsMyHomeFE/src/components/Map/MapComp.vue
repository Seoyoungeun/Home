<template>
  <div id="map" style="width: 100%; height: 100%" ref="mapContainer"></div>
</template>

<script setup>
import { aptAi } from '@/axios'
import { ref, watch } from 'vue'
import { useTemplateRef, onMounted } from 'vue'
import { watchEffect } from 'vue'
let map
let geocoder
let clusterer

const token = localStorage.getItem('jwt')
const user = JSON.parse(localStorage.getItem('user'))

const mapContainer = ref(null) // 지도를 표시할 div
const mapOption = {
  center: new window.kakao.maps.LatLng(37.495, 127.027), // 지도의 중심좌표
  level: 8, // 지도의 확대 레벨
}

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
onMounted(() => {
  map = new window.kakao.maps.Map(mapContainer.value, mapOption)
  geocoder = new kakao.maps.services.Geocoder()
  clusterer = new kakao.maps.MarkerClusterer({
    map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
    averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
    minLevel: 2, // 클러스터 할 최소 지도 레벨
  })

  var mapTypeControl = new kakao.maps.MapTypeControl()
  map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT)
  var zoomControl = new kakao.maps.ZoomControl()
  map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT)

  // 초기 검색
  search()

  // 맵 이동시 다시 검색
  kakao.maps.event.addListener(map, 'idle', function () {
    search()
  })
})

const props = defineProps({
  apt_list: Array,
  apt_selected: String,
  keyword: String,
  region: String,
  moveToAdr: String,
})

const condition = ref({
  swLat: '37.495',
  swLng: '127.027',
  neLat: '37.505',
  neLng: '127.037',
  keyword: '',
})

const emit = defineEmits(['updated:apt_list', 'updated:apt_selected', 'updated:region'])

// Marker 관리 영역 =============================================
import markerImg from '@/assets/images/marker.png'
import markerSelectedImg from '@/assets/images/marker_selected.png'

const imageSize = new kakao.maps.Size(40, 40)
const normalImage = new kakao.maps.MarkerImage(markerImg, imageSize)

const imageSelectedSize = new kakao.maps.Size(50, 50)
const clickImage = new kakao.maps.MarkerImage(markerSelectedImg, imageSelectedSize)

const mark = async (filteredList) => {
  if (!filteredList) return

  const newMarkers = []

  // 1. 기존 마커 지우기
  clusterer.clear()

  // 2. 새로운 마커 추가
  for (let i = 0; i < filteredList.length; i++) {
    const isSelected = filteredList[i].apt_seq === props.apt_selected
    const marker = new kakao.maps.Marker({
      position: new kakao.maps.LatLng(filteredList[i].latitude, filteredList[i].longitude),
      title: filteredList[i].apt_nm,
      image: isSelected ? clickImage : normalImage,
    })

    kakao.maps.event.addListener(marker, 'click', function () {
      // 클릭된 마커를 현재 클릭된 마커 객체로 설정합니다
      emit('updated:apt_selected', filteredList[i].apt_seq)
    })

    newMarkers.push(marker) // 마커 배열에 저장
  }

  clusterer.addMarkers(newMarkers)
}

const search = async () => {
  if (!map) return

  // 지도 영역정보를 얻어옵니다
  const bounds = map.getBounds()
  const swLatlng = bounds.getSouthWest()
  const neLatlng = bounds.getNorthEast()

  // 아파트 리스트 검색을 위한 파라미터 수정
  condition.value.swLat = swLatlng.getLat()
  condition.value.swLng = swLatlng.getLng()
  condition.value.neLat = neLatlng.getLat()
  condition.value.neLng = neLatlng.getLng()

  condition.value.keyword = props.keyword || ''

  try {
    const response = await aptAi.get('/api/v1/apts', { params: condition.value })
    const temp = response.data.data.items

    let filteredList

    const zoom = map.getLevel()
    if (zoom < 6) {
      filteredList = temp
    } else {
      let resolution
      if (zoom < 7) {
        resolution = 0.0025
      } else if (zoom < 8) {
        resolution = 0.005
      } else if (zoom < 9) {
        resolution = 0.01
      } else if (zoom < 10) {
        resolution = 0.02
      } else {
        resolution = 0.04
      }

      const seenGrids = new Set()

      filteredList = temp.filter((apt) => {
        const gridKey = `${Math.floor(apt.latitude / resolution)}_${Math.floor(apt.longitude / resolution)}`
        if (seenGrids.has(gridKey)) return false
        seenGrids.add(gridKey)
        return true
      })
    }
    mark(filteredList)
    emit('updated:apt_list', filteredList)
  } catch (error) {
    console.log('목록 조회 실패' + error.name)
  }
}

const moveMap = (location, level) => {
  if (!location || location === '') return
  geocoder.addressSearch(location, function (result, status) {
    // 정상적으로 검색이 완료됐으면
    if (status === kakao.maps.services.Status.OK) {
      var coords = new kakao.maps.LatLng(result[0].y, result[0].x)
      map.jump(coords, level)
      emit('updated:region')
    }
  })
}

watch(
  [() => props.keyword, () => props.apt_selected, () => props.region, () => props.moveToAdr],
  (
    [newKeyword, newSelected, newRegion, newMoveToAdr],
    [oldKeyword, oldSelected, oldRegion, oldMoveToAdr],
  ) => {
    if (newSelected !== oldSelected && (newSelected === null || oldSelected === null)) {
      if (!map) return
      map.relayout()
    }

    if (newKeyword !== oldKeyword || newSelected !== oldSelected) {
      // 키워드나 선택된 아파트가 바뀌었을때때
      search()
    } else {
      console.log('newMoveToAdr')
      if (newRegion !== oldRegion) {
        moveMap(props.region, 6)
      } else if (newMoveToAdr !== oldMoveToAdr) {
        moveMap(newMoveToAdr, 1)
      }
    }
  },
  { immediate: true },
)
</script>

<style scoped></style>
