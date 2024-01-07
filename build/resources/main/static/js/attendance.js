
let a = 10;
document.getElementById("percent").innerText=a+"%";
window.onload = function(){
    if(member == null){
        alert("돌아가라")
    }else{
        alert(member.memberName+"님, 환영합니다");
    }
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

    //click start button
    start.addEventListener("click", function(){
        getExternalIp().then(ip =>{
            goCheckIn().then(ok=>{
                if ( myIp === myIp) {
                    start.disabled = true;
                    stop.disabled = false;
                    if(timer > 0){
                        return;
                    }
                    var sec = parseInt(document.getElementById("sec").innerText);
                    var min = parseInt(document.getElementById("min").innerText);
                    var hour = parseInt(document.getElementById("hour").innerText);

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

                }else{
                    alert("당신지금 어디야");
                }
            }).catch(error=>{
                alert("출근하는데 문제가 생겼어요..\n에러는 : ",error)
            })



        }).catch(err=>{
            alert("ip를 받아오는데 문제가 발생했어요\n에러는 : ",err);
        })

    });
    //click stop button
    stop.addEventListener("click", function(){
        if(hour.innerText<2){
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
    });
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
                    '#00B4ED',
                    '#5CD2E6'
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
