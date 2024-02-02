
let myIp = "220.69";
let start = document.getElementById("start");
let stop = document.getElementById("stop");
let noWork = document.getElementById("noWork")
let logOut = document.getElementById("log-out")
stop.disabled = true;
let timer_sec;
let timer_min;
let timer_hour;
let timer_micro;
let timer = 0;
let a = 10;

let color1 = '#00B4ED';
let color2 = '#5CD2E6';
var context = document
    .getElementById('myChart')
    .getContext('2d');
var myChart = new Chart(context, {
    type: 'doughnut', // 차트의 형태
    data: { // 차트에 들어갈 데이터
        datasets: [
            { //데이터
                label: [
                    '근무량',
                    '남은근무'
                ], //차트 제목
                data: [
                    a,100-a //x축 label에 대응되는 데이터 값
                ],
                backgroundColor: [
                    //색상
                    color1,
                    color2
                ],
                borderWidth: 0 //경계선 굵기
            }
        ]
    },
    options: {
        scales: {
            yAxes: [
                {
                    ticks: {
                        beginAtZero: true
                    }
                }
            ]
        }
    }
});

document.getElementById("percent").innerText=a+"%";

window.onload = function(){
    const darkModeSetting = localStorage.getItem('darkMode');
    if(darkModeSetting === 'enabled') {
        document.body.classList.add('dark-mode');
        darkModeToggle.checked = true; // 토글 버튼 상태 업데이트
        myChart.update();

    }
    if(member == null){
        alert("돌아가라")
    }else{
        alert(member.memberName+"님, 환영합니다");
    //    세션에 출근상태인지 확인
        if (myRecentAttendance) {
            let tf = confirm("출근시간을 이어서 하시겠습니까?");
            if(tf){
                let today = new Date(); // 현재 시간
                let myRecentAttendance1 = new Date(myRecentAttendance); // 최근 출석 시간
                console.log(myRecentAttendance1);
                let differenceInMillis = today.getTime() - myRecentAttendance1.getTime();

                let differenceInSeconds = Math.floor(differenceInMillis / 1000);
                let differenceInMinutes = Math.floor(differenceInSeconds / 60);
                let differenceInHours = Math.floor(differenceInMinutes / 60);

            // 시간, 분, 초로 변환
                let hours = Math.floor(differenceInHours);
                let minutes = Math.floor(differenceInMinutes % 60);
                let seconds = Math.floor(differenceInSeconds % 60);

                if(hours<10){
                    document.getElementById("hour").innerText ="0"+ hours;
                }else if(hours === 0){
                    document.getElementById("hour").innerText ="00";
                }else{
                    document.getElementById("hour").innerText = hours.toString();
                }
                if(minutes<10){
                    document.getElementById("min").innerText ="0"+  minutes;
                }else if(minutes === 0){
                    document.getElementById("min").innerText ="00";
                }else{
                    document.getElementById("min").innerText =minutes.toString();
                }
                if(seconds<10){
                    document.getElementById("sec").innerText =  "0"+seconds;
                }else if(seconds === 0){
                    document.getElementById("sec").innerText =  "00";
                }else{
                    document.getElementById("sec").innerText =  seconds.toString();

                }

                startStopWatch();
            }
        }
    }
}
async function getExternalIp() {
    try {
        const response = await fetch('https://ipinfo.io/json');
        const data = await response.json();
        const ipAddress = data.ip;
        return ipAddress.split(".")[0] + "." + ipAddress.split(".")[1];
    } catch (error) {
        console.error('외부 IP 주소를 가져오는 중 오류 발생:', error);
    }
}

document.getElementById("log-out").addEventListener('click', () => {
    let tf = confirm("로그아웃 하시겠습니까?");
    if (tf) {
        window.location.href = '/member/logout';
    }
})


//click start button
start.addEventListener("click",getCheckIn);
//click stop button
stop.addEventListener("click", getCheckOut);


function startStopWatch() {
    start.disabled = true;
    stop.disabled = false;
    if(timer > 0){
        return;
    }
    let sec = parseInt(document.getElementById("sec").innerText);
    let min = parseInt(document.getElementById("min").innerText);
    let hour = parseInt(document.getElementById("hour").innerText);

    //start seconds
    timer_sec = setInterval(function(){
        //console.log(i);
        sec++;
        if(sec == 60) {
            sec = "00";
        } else if(sec < 10){
            sec = "0" + sec;
        }
        document.getElementById("sec").innerText = sec;
    }, 1000);

    //start minutes
    timer_min = setInterval(function(){
        min++;

        if(min == 60) {
            min = 0;
        } else if(min < 10){
            min = "0" + min;
        }

        document.getElementById("min").innerText = min;
    }, 60000);

    //start hours
    timer_hour = setInterval(function(){
        //console.log(hour);
        hour++;

        if(hour < 10){
            hour = "0" + hour;
        }

        document.getElementById("hour").innerText = hour;
        console.log(hour)
    }, 3600000);

    timer++;

}
function getCheckIn(){
    getExternalIp().then(ip =>{
        if ( ip === myIp) {
            console.log(ip);
            startStopWatch();
            goCheckIn().catch(error=>{
                alert("출근하는데 문제가 생겼어요..\n에러는 : ",error)
            })

        }else{
            alert("당신지금 어디야");
        }



    }).catch(err=>{
        alert("ip를 받아오는데 문제가 발생했어요\n에러는 : ",err);
    })

}
function getCheckOut(){
    if(hour.innerText<5){
        let tF= confirm("시간을 충족하지 못했습니다. 그래도 퇴근하시겠습니까?");
        if(tF){
            document.getElementById("msg").innerText = "어서 더 채워 주세요!";
            clearInterval(timer_micro);
            clearInterval(timer_sec);
            clearInterval(timer_min);
            clearInterval(timer_hour);

            timer--;
            if(timer < 0){
                timer = 0;
            }
            goCheckOut().then(res =>{
                start.disabled = false;
                stop.disabled = true;
            }).catch(err=>{
                alert("퇴근하는데 문제가 생겼어요..\n에러는 : ",err);
            })
        }else{
            start.disabled = true;
            stop.disabled = false;

        }
    }else{
        document.getElementById("msg").innerText = "오늘의 할당량을 마치셨습니다!";
        clearInterval(timer_micro);
        clearInterval(timer_sec);
        clearInterval(timer_min);
        clearInterval(timer_hour);

        timer--;
        if(timer < 0){
            timer = 0;
        }
        goCheckOut().then(res =>{
            start.disabled = false;
            stop.disabled = true;
        }).catch(err=>{
            alert("퇴근하는데 문제가 생겼어요..\n에러는 : ",err);
        })
    }
}



// 출근 로직
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
            alert("세션이 종료되었습니다. 다시 로그인해주세요")
        } else if (response.ok) {
            return response;
        } else {
            return 'Error during check-in' ;
        }
    } catch (error) {
        return error;
    }
}

// 퇴근 로직
async function goCheckOut(){
    try{
        const response = await fetch('/api/checkOut',{
            method:'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({}),
        });

        if (response.status === 404) {
            alert('세션이 종료되었습니다. 다시 로그인해주세요');
        } else if (response.ok) {
            return response;
        } else {
            return 'Error during check-in' ;
        }
    } catch (error) {
        return error;
    }
}
document.getElementById('noWork').addEventListener('click', async (e) => {
    e.preventDefault();

    // 선택된 범주 값을 가져오기
    const categorySelect = document.getElementById('categorySelect');
    const selectedCategory = categorySelect.options[categorySelect.selectedIndex].value;

    // 입력된 텍스트 가져오기
    const detailTextarea = document.getElementById('detailTextarea');
    const detailText = detailTextarea.value;

    console.log(selectedDateInput.value);
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
            alert('세션이 종료되었습니다. 다시 로그인해주세요');
            window.location = '/member/login';
        } else if (response.ok) {
            alert("제출되었습니다.")
            return response;
        } else {
            alert("오류입니다");
        }
    } catch (error) {
        return error;
    }
});



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


function showMoreNoWork(){
    let hiddenRows = Array.from(document.querySelectorAll('.hidden-noWork'));
    let nextRows = hiddenRows.slice(0, 5);
    nextRows.forEach(function(row) {
        row.classList.remove('hidden-noWork');
    });

    // "더보기" 버튼을 숨김 처리하기 위한 조건 검사
    if (hiddenRows.length <= 5) {
        document.getElementById('noWork-show-more-btn').style.display = 'none';
    }
}

darkModeToggle.addEventListener('change', function() {
    // 먼저 다크 모드 상태에 따라 색상을 결정합니다.
    const isDarkModeEnabled = document.body.classList.contains('dark-mode');
    color1 = isDarkModeEnabled ? '#00B4ED' : '#000';
    color2 = isDarkModeEnabled ? '#5CD2E6' : '#393939';

    // 그 후에 다크 모드 상태를 토글합니다.
    document.body.classList.toggle('dark-mode');

    // 차트 데이터 업데이트
    myChart.data.datasets[0].backgroundColor[0] = color1;
    myChart.data.datasets[0].backgroundColor[1] = color2;

    // 차트 업데이트
    myChart.update();

    // 마지막으로 변경된 다크 모드 상태를 localStorage에 저장합니다.
    if(document.body.classList.contains('dark-mode')) {
        localStorage.setItem('darkMode', 'enabled');
    } else {
        localStorage.setItem('darkMode', 'disabled');
    }
});
