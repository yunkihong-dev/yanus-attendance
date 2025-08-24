
// 전역 변수
let myIp = "220.69";
let start = document.getElementById("start");
let stop = document.getElementById("stop");
let noWork = document.getElementById("noWork");
let logOut = document.getElementById("log-out");
let darkModeToggle = document.getElementById('darkModeToggle');

// 타이머 관련 변수
let timer_sec, timer_min, timer_hour, timer_micro;
let timer = 0;
let a = 10;

// 차트 색상
let color1 = '#00B4ED';
let color2 = '#5CD2E6';

// 초기화
stop.disabled = true;

// 유틸리티 함수들
function showReusableModal(message) {
    const modal = document.getElementById("reusableModal");
    const modalMessage = document.getElementById("reusableModalMessage");
    modalMessage.textContent = message;
    modal.style.display = "block";

    const span = document.getElementById("reusable-modal-close");
    span.onclick = function() {
        modal.style.display = "none";
    }
    
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
}

function handleConfirm(response) {
    const modal = document.getElementById("confirmModal");
    modal.style.display = "none";
    return response;
}

function showConfirmModal(message) {
    return new Promise((resolve, reject) => {
        const modal = document.getElementById("confirmModal");
        const modalMessage = document.getElementById("confirmModalMessage");
        modalMessage.textContent = message;
        modal.style.display = "block";

        window.handleConfirm = function(response) {
            modal.style.display = "none";
            resolve(response);
            delete window.handleConfirm;
        };
    });
}

// 차트 초기화
const context = document.getElementById('myChart').getContext('2d');
let myChart = new Chart(context, {
    type: 'doughnut',
    data: {
        datasets: [{
            label: ['근무량', '남은근무'],
            data: [a, 100 - a],
            backgroundColor: [color1, color2],
            borderWidth: 0
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                display: false
            }
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});

// 차트 색상 업데이트 함수
function updateChartColorsForDarkMode(isDarkMode) {
    color1 = isDarkMode ? '#000' : '#00B4ED';
    color2 = isDarkMode ? '#393939' : '#5CD2E6';

    myChart.data.datasets[0].backgroundColor[0] = color1;
    myChart.data.datasets[0].backgroundColor[1] = color2;
    myChart.update();
}

// 페이지 로드 시 초기화
window.onload = function() {
    // 다크 모드 초기화
    const darkModeSetting = localStorage.getItem('darkMode');
    if (darkModeSetting === 'enabled') {
        document.body.classList.add('dark-mode');
        darkModeToggle.checked = true;
        updateChartColorsForDarkMode(true);
    } else {
        updateChartColorsForDarkMode(false);
    }

    // 환영 메시지 및 출근 상태 확인
    if (member == null) {
        showReusableModal("로그인이 필요합니다.");
    } else {
        showReusableModal(member.memberName + "님, 환영합니다");
        
        // 세션에 출근상태인지 확인
        if (myRecentAttendance) {
            showConfirmModal("출근시간을 이어서 하시겠습니까?").then(res => {
                if (res) {
                    continueWorkTime();
                }
            });
        }
    }
};

// 출근 시간 이어서 하기
function continueWorkTime() {
    const today = new Date();
    const myRecentAttendance1 = new Date(myRecentAttendance);
    const differenceInMillis = today.getTime() - myRecentAttendance1.getTime();

    const differenceInSeconds = Math.floor(differenceInMillis / 1000);
    const differenceInMinutes = Math.floor(differenceInSeconds / 60);
    const differenceInHours = Math.floor(differenceInMinutes / 60);

    const hours = Math.floor(differenceInHours);
    const minutes = Math.floor(differenceInMinutes % 60);
    const seconds = Math.floor(differenceInSeconds % 60);

    updateTimeDisplay(hours, minutes, seconds);
    startStopWatch();
}

// 시간 표시 업데이트
function updateTimeDisplay(hours, minutes, seconds) {
    document.getElementById("hour").innerText = hours < 10 ? "0" + hours : hours === 0 ? "00" : hours.toString();
    document.getElementById("min").innerText = minutes < 10 ? "0" + minutes : minutes === 0 ? "00" : minutes.toString();
    document.getElementById("sec").innerText = seconds < 10 ? "0" + seconds : seconds === 0 ? "00" : seconds.toString();
}

// 외부 IP 주소 가져오기
async function getExternalIp() {
    try {
        const response = await fetch('https://ipinfo.io/json');
        const data = await response.json();
        const ipAddress = data.ip;
        return ipAddress.split(".")[0] + "." + ipAddress.split(".")[1];
    } catch (error) {
        console.error('외부 IP 주소를 가져오는 중 오류 발생:', error);
        throw error;
    }
}

// 로그아웃 이벤트
document.getElementById("log-out").addEventListener('click', () => {
    showConfirmModal("로그아웃 하시겠습니까?").then(res => {
        if (res) {
            window.location.href = '/member/logout';
        }
    });
});

// 출근 버튼 이벤트
start.addEventListener("click", getCheckIn);

// 퇴근 버튼 이벤트
stop.addEventListener("click", getCheckOut);

// 스톱워치 시작
function startStopWatch() {
    start.disabled = true;
    stop.disabled = false;
    
    if (timer > 0) {
        return;
    }
    
    let sec = parseInt(document.getElementById("sec").innerText);
    let min = parseInt(document.getElementById("min").innerText);
    let hour = parseInt(document.getElementById("hour").innerText);

    // 초 타이머
    timer_sec = setInterval(function() {
        sec++;
        if (sec == 60) {
            sec = "00";
        } else if (sec < 10) {
            sec = "0" + sec;
        }
        document.getElementById("sec").innerText = sec;
    }, 1000);

    // 분 타이머
    timer_min = setInterval(function() {
        min++;
        if (min == 60) {
            min = 0;
        } else if (min < 10) {
            min = "0" + min;
        }
        document.getElementById("min").innerText = min;
    }, 60000);

    // 시간 타이머
    timer_hour = setInterval(function() {
        hour++;
        if (hour < 10) {
            hour = "0" + hour;
        }
        document.getElementById("hour").innerText = hour;
    }, 3600000);

    timer++;
}

// 출근 처리
function getCheckIn() {
    getExternalIp().then(ip => {
        if (ip === myIp) {
            console.log(ip);
            startStopWatch();
            goCheckIn().catch(error => {
                showReusableModal("출근하는데 문제가 생겼어요..\n에러는 : " + error);
            });
        } else {
            showReusableModal("당신지금 어디야");
        }
    }).catch(err => {
        showReusableModal("ip를 받아오는데 문제가 발생했어요\n에러는 : " + err);
    });
}

// 퇴근 처리
function getCheckOut() {
    const hourText = document.getElementById("hour").innerText;
    const hourValue = parseInt(hourText);
    
    if (hourValue < 3) {
        showConfirmModal("시간을 충족하지 못했습니다. 그래도 퇴근하시겠습니까?").then(res => {
            if (res) {
                finishWork("어서 더 채워 주세요!");
            } else {
                start.disabled = true;
                stop.disabled = false;
            }
        });
    } else {
        finishWork("오늘의 할당량을 마치셨습니다!");
    }
}

// 퇴근 완료 처리
function finishWork(message) {
    document.getElementById("msg").innerText = message;
    
    clearInterval(timer_micro);
    clearInterval(timer_sec);
    clearInterval(timer_min);
    clearInterval(timer_hour);

    timer--;
    if (timer < 0) {
        timer = 0;
    }
    
    goCheckOut().then(res => {
        start.disabled = false;
        stop.disabled = true;
    }).catch(err => {
        showReusableModal("퇴근하는데 문제가 생겼어요..\n에러는 : " + err);
    });
}

// 출근 API 호출
async function goCheckIn() {
    try {
        const response = await fetch('/api/checkIn', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({}),
        });

        if (response.status === 404) {
            showReusableModal("세션이 종료되었습니다. 다시 로그인해주세요");
        } else if (response.ok) {
            return response;
        } else {
            return 'Error during check-in';
        }
    } catch (error) {
        return error;
    }
}

// 퇴근 API 호출
async function goCheckOut() {
    try {
        const response = await fetch('/api/checkOut', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({}),
        });

        if (response.status === 404) {
            showReusableModal('세션이 종료되었습니다. 다시 로그인해주세요');
        } else if (response.ok) {
            return response;
        } else {
            return 'Error during check-out';
        }
    } catch (error) {
        return error;
    }
}

// 미출근 사유 제출
document.getElementById('noWork').addEventListener('click', async (e) => {
    e.preventDefault();

    const categorySelect = document.getElementById('categorySelect');
    const selectedCategory = categorySelect.options[categorySelect.selectedIndex].value;
    const detailTextarea = document.getElementById('detailTextarea');
    const detailText = detailTextarea.value;

    if (!selectedCategory || selectedCategory === '범주를 선택하세요') {
        showReusableModal("범주를 선택해주세요.");
        return;
    }

    try {
        const response = await fetch('/api/noWork', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                category: selectedCategory,
                detail: detailText,
                selectedDate: selectedDateInput.value
            }),
        });

        if (response.status === 404) {
            showReusableModal('세션이 종료되었습니다. 다시 로그인해주세요');
            window.location = '/member/login';
        } else if (response.ok) {
            showReusableModal("제출되었습니다.");
            // 모달 닫기
            document.getElementById('modalContainer').classList.add('hidden');
            // 폼 리셋
            categorySelect.selectedIndex = 0;
            detailTextarea.value = '';
            return response;
        } else {
            showReusableModal("오류입니다");
        }
    } catch (error) {
        showReusableModal("제출 중 오류가 발생했습니다.");
        return error;
    }
});

// 모달 관련 이벤트
const modalOpenButton = document.getElementById('modalOpenButton');
const modalCloseButton = document.getElementById('modalCloseButton');
const modal = document.getElementById('modalContainer');

modalOpenButton.addEventListener('click', () => {
    modal.classList.remove('hidden');
});

modalCloseButton.addEventListener('click', (e) => {
    e.preventDefault();
    modal.classList.add('hidden');
});

// 더보기 버튼
function showMoreNoWork() {
    const hiddenRows = Array.from(document.querySelectorAll('.hidden-noWork'));
    const nextRows = hiddenRows.slice(0, 5);
    
    nextRows.forEach(function(row) {
        row.classList.remove('hidden-noWork');
    });

    if (hiddenRows.length <= 5) {
        document.getElementById('noWork-show-more-btn').style.display = 'none';
    }
}

// 다크 모드 토글
darkModeToggle.addEventListener('change', function() {
    const isDarkModeEnabled = document.body.classList.contains('dark-mode');
    color1 = isDarkModeEnabled ? '#00B4ED' : '#000';
    color2 = isDarkModeEnabled ? '#5CD2E6' : '#393939';

    document.body.classList.toggle('dark-mode');

    myChart.data.datasets[0].backgroundColor[0] = color1;
    myChart.data.datasets[0].backgroundColor[1] = color2;
    myChart.update();

    if (document.body.classList.contains('dark-mode')) {
        localStorage.setItem('darkMode', 'enabled');
    } else {
        localStorage.setItem('darkMode', 'disabled');
    }
});

// 차트 퍼센트 표시 업데이트
document.getElementById("percent").innerText = a + "%";

// 키보드 이벤트 (ESC로 모달 닫기)
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        const modals = document.querySelectorAll('.modal, .alert-modal, .confirm-modal');
        modals.forEach(modal => {
            if (!modal.classList.contains('hidden') && modal.style.display !== 'none') {
                modal.classList.add('hidden');
                modal.style.display = 'none';
            }
        });
    }
});

// 페이지 언로드 시 타이머 정리
window.addEventListener('beforeunload', function() {
    if (timer_sec) clearInterval(timer_sec);
    if (timer_min) clearInterval(timer_min);
    if (timer_hour) clearInterval(timer_hour);
    if (timer_micro) clearInterval(timer_micro);
});
