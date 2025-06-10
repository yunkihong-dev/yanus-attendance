let darkModeToggle = document.getElementById("darkModeToggle");

function showReusableModal(message) {
    let modal = document.getElementById("reusableModal");
    let modalMessage = document.getElementById("reusableModalMessage");
    modalMessage.textContent = message;
    modal.style.display = "block";

    let span = document.getElementById("reusable-modal-close");
    span.onclick = function() {
        modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }
}
// 확인 또는 취소 버튼 클릭 시 호출되는 함수
function handleConfirm(response) {
    var modal = document.getElementById("confirmModal");
    modal.style.display = "none";
    return response; // 반환 값으로 true 또는 false를 반환합니다.
}

// confirm 모달을 표시하는 함수
function showConfirmModal(message) {
    return new Promise((resolve, reject) => {
        let modal = document.getElementById("confirmModal");
        var modalMessage = document.getElementById("confirmModalMessage");
        modalMessage.textContent = message;
        modal.style.display = "block";

        // 사용자의 입력을 resolve합니다.
        window.handleConfirm = function(response) {
            modal.style.display = "none";
            resolve(response);
            delete window.handleConfirm; // 이벤트 핸들러를 삭제합니다.
        };
    });
}

window.onload = function() {
    const darkModeSetting = localStorage.getItem('darkMode');
    if (darkModeSetting === 'enabled') {
        document.body.classList.add('dark-mode');
        darkModeToggle.checked = true; // 토글 버튼 상태 업데이트
    }
}

function selectAll(selectAll){
    const checkboxes = document.getElementsByName('members');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })
}

function selectAllNoWork(selectAll){
    const checkboxes = document.getElementsByName('noWorks');

    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })
}

document.getElementById("log-out").addEventListener('click', () => {
    showConfirmModal("로그아웃 하시겠습니까?").then(res => {
        if (res) {
            window.location.href = '/member/logout';
        }
    })

})

//로그아웃 버튼
document.getElementById('logout-text').addEventListener('mouseover', function () {
    this.textContent = '로그아웃';
});

document.getElementById('logout-text').addEventListener('mouseout', function () {
    this.textContent = member.memberName+ ' 관리자님, 반갑습니다!';
});


function showMore() {
    let hiddenRows = Array.from(document.querySelectorAll('.hidden-member'));
    let nextRows = hiddenRows.slice(0, 5);
    nextRows.forEach(function(row) {
        row.classList.remove('hidden-member');
    });

    // "더보기" 버튼을 숨김 처리하기 위한 조건 검사
    if (hiddenRows.length <= 5) {
        document.getElementById('show-more-btn').style.display = 'none';
    }
}

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


// fetch API를 사용하여 서버로 selectedItems를 전송
function OkToNoWork() {
    const selectedItems = Array.from(document.querySelectorAll('input[type="checkbox"][name="noWorks"]:checked')).map(checkbox => checkbox.value);

    if(selectedItems[0] === 'selectAll'){
        selectedItems.shift();
    }
    if(selectedItems.length === 0 ){
        showReusableModal("한개 이상 선택해주세요");
        return;
    }
    // AJAX 또는 Fetch API를 사용하여 서버로 selectedItems를 전송
    fetch('/api/NoWorkOk', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedItems),
    })
        .then(response => response.text()) // 응답을 텍스트로 처리
        .then(data => {
            showReusableModal(data); // "정상처리 되었습니다" 출력
            for(let value of selectedItems){
                document.getElementById(value).innerText= "승인";
            }
        })
        .catch(error => {
            showReusableModal('Error:', error);
        });

}

function NotOkToNoWork() {
    const selectedItems = Array.from(document.querySelectorAll('input[type="checkbox"][name="noWorks"]:checked')).map(checkbox => checkbox.value);

    if(selectedItems[0] === 'selectAll'){
        selectedItems.shift();
    }
    if(selectedItems.length ===0){
        showReusableModal("한개 이상 선택해주세요");
        return;
    }
    //fetch API를 사용하여 서버로 selectedItems를 전송
    fetch('/api/NoWorkNotOk', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedItems),
    })
        .then(response => response.text()) // 응답을 텍스트로 처리
        .then(data => {
            showReusableModal(data); // "정상처리 되었습니다" 출력
            for (let value of selectedItems) {
                document.getElementById(value).innerText = "거절";
            }
        })
        .catch(error => {
            showReusableModal('Error:', error);
        });

}



darkModeToggle.addEventListener('change', function() {
    // 먼저 다크 모드 상태에 따라 색상을 결정.
    const isDarkModeEnabled = document.body.classList.contains('dark-mode');
    color1 = isDarkModeEnabled ? '#00B4ED' : '#000';
    color2 = isDarkModeEnabled ? '#5CD2E6' : '#393939';

    // 그 후에 다크 모드 상태를 토글.
    document.body.classList.toggle('dark-mode');

    // 마지막으로 변경된 다크 모드 상태를 localStorage에 저장.
    if(document.body.classList.contains('dark-mode')) {
        localStorage.setItem('darkMode', 'enabled');
    } else {
        localStorage.setItem('darkMode', 'disabled');
    }
});

document.addEventListener("DOMContentLoaded", (event) => {
    const form = document.getElementById("attendanceForm");
    const dropdown = document.getElementById("myDropdown");
    const toggleButton = dropdown.querySelector(".dropdown-toggle");
    const dropdownContent = dropdown.querySelector(".dropdown-content");
    const deselectAllButton = document.getElementById("deselect-dropdown");
    const closeButton = document.getElementById("close-dropdown");
    const options = dropdownContent.querySelectorAll("a");
    const dateStart = document.getElementById("date-start");
    const dateEnd = document.getElementById("date-end");

    // 선택된 팀원들의 ID를 저장할 배열
    let selectedMembers = [];

    // URL에서 쿼리 스트링 읽기
    const urlParams = new URLSearchParams(window.location.search);
    const from = urlParams.get("from");
    const to = urlParams.get("to");
    const memberIds = urlParams.getAll("memberIds");

    // 필터 값을 초기화
    if (from) {
        dateStart.value = from;
    }
    if (to) {
        dateEnd.value = to;
    }
    if (memberIds.length > 0) {
        options.forEach(option => {
            const memberId = option.getAttribute("data-value");
            if (memberIds.includes(memberId)) {
                option.classList.add("selected");
                selectedMembers.push(memberId);
            }
        });
    }

    // 버튼 클릭 시 드롭다운 열기/닫기
    toggleButton.addEventListener("click", (event) => {
        event.stopPropagation();
        dropdown.classList.toggle("active");
    });

    // 옵션 클릭 시 선택 상태 변경 및 선택된 멤버 ID 저장
    options.forEach(option => {
        option.addEventListener("click", (event) => {
            event.preventDefault();
            option.classList.toggle("selected");

            const memberId = option.getAttribute("data-value");
            if (option.classList.contains("selected")) {
                if (!selectedMembers.includes(memberId)) {
                    selectedMembers.push(memberId);
                }
            } else {
                selectedMembers = selectedMembers.filter(id => id !== memberId);
            }
        });
    });

    // 전체 선택 해제
    deselectAllButton.addEventListener("click", ()=>{
        options.forEach(o => o.classList.remove("selected"))
        selectedMembers = [];

    })
    // 닫기 버튼 클릭 시 드롭다운 닫기
    closeButton.addEventListener("click", () => {
        dropdown.classList.remove("active");
    });

    // 오늘 버튼 클릭 시 날짜 설정
    document.getElementById("todayButton").addEventListener("click", () => {
        const today = new Date().toISOString().split('T')[0];
        dateStart.value = today;
        dateEnd.value = today;
    });

    // 폼 제출 시 유효성 검사
    form.addEventListener("submit", (event) => {
        event.preventDefault();

        // 날짜 유효성 검사
        if (!dateStart.value || !dateEnd.value) {
            alert("시작일과 종료일을 모두 선택해주세요.");
            return;
        }

        if (dateStart.value > dateEnd.value) {
            alert("종료일은 시작일보다 이후여야 합니다.");
            return;
        }

        // 팀원 선택 유효성 검사
        if (selectedMembers.length === 0) {
            alert("최소 한 명의 팀원을 선택해주세요.");
            return;
        }

        // 선택된 멤버 ID를 hidden input으로 추가
        const existingMemberInputs = form.querySelectorAll('input[name="memberIds"]');
        existingMemberInputs.forEach(input => input.remove());

        selectedMembers.forEach(memberId => {
            const hiddenInput = document.createElement("input");
            hiddenInput.type = "hidden";
            hiddenInput.name = "memberIds";
            hiddenInput.value = memberId;
            form.appendChild(hiddenInput);
        });

        // 폼 제출
        form.submit();
    });

    // 문서 클릭 시 드롭다운 닫기
    document.addEventListener("click", (event) => {
        if (!dropdown.contains(event.target)) {
            dropdown.classList.remove("active");
        }
    });
});

document.getElementById('delete-member-btn').addEventListener('click', async () => {
    const memberIds = Array.from(document.querySelectorAll('input[type="checkbox"][name="members"]:checked')).map(checkbox => checkbox.value);


    console.log(memberIds);
    if (memberIds.length === 0) {
        showReusableModal("삭제할 회원을 선택하세요.");
        return;
    }
    console.log(memberIds);
    showConfirmModal("선택한 회원들을 삭제하시겠습니까?").then(async (res) => {
        if (!res) return;

        try {
            const response = await fetch('/member/deleteMembers', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]')?.getAttribute('content') || ''
                },
                body: JSON.stringify(memberIds)
            });

            const result = await response.json();

            if (response.ok && result.status === "success") {
                showReusableModal(result.message);
                setTimeout(() => {
                    location.reload();
                }, 1000); // 1초 후 새로고침

            } else {
                showReusableModal(result.message || "회원 삭제에 실패했습니다.");
            }
        } catch (error) {
            console.error("삭제 중 오류 발생", error);
            showReusableModal("회원 삭제 중 오류가 발생했습니다.");
        }
    });
});


// 모달 열기
function openAttendanceModal() {
    const modal = document.getElementById("attendance-modal");
        modal.style.display = "grid";
}

// 모달 닫기
function closeAttendanceModal() {
    const modal = document.getElementById("attendance-modal");
        modal.style.display = "none";
}

// 폼 데이터 검증
function validateAttendanceForm({ startDate, endDate, weekdays, startTime, endTime }) {
    if (!startDate || !endDate) {
        alert("시작 날짜와 종료 날짜를 입력해주세요.");
        return false;
    }
    if (startDate > endDate) {
        alert("종료 날짜는 시작 날짜 이후여야 합니다.");
        return false;
    }
    if (weekdays.length === 0) {
        alert("최소 하나의 요일을 선택해주세요.");
        return false;
    }
    if (!startTime || !endTime) {
        alert("출근 시간과 퇴근 시간을 모두 입력해주세요.");
        return false;
    }
    return true;
}

// 폼 제출 처리
function handleAttendanceFormSubmit(event, form) {
    event.preventDefault();

    const formData = new FormData(form);
    const startDate = formData.get("startDate");
    const endDate = formData.get("endDate");
    const weekdays = formData.getAll("weekday");
    const startTime = formData.get("startTime");
    const endTime = formData.get("endTime");

    const data = { startDate, endDate, weekdays, startTime, endTime };

    if (validateAttendanceForm(data)) {
        console.log("폼 데이터:", data);
        alert("출근 시간이 저장되었습니다.");
        closeAttendanceModal(form);
    }
}

function sortTable(header, columnIndex) {
    const table = header.closest("table");
    const tableBody = table.querySelector("tbody");
    const rows = Array.from(tableBody.querySelectorAll("tr"));

    // 정렬 상태 확인
    const isAscending = header.getAttribute("data-sort-order") !== "asc";
    header.setAttribute("data-sort-order", isAscending ? "asc" : "desc");

    // 정렬 로직
    rows.sort((rowA, rowB) => {
        const cellA = rowA.children[columnIndex]?.textContent.trim() || "";
        const cellB = rowB.children[columnIndex]?.textContent.trim() || "";

        if (isAscending) {
            return cellA.localeCompare(cellB, undefined, { numeric: true });
        } else {
            return cellB.localeCompare(cellA, undefined, { numeric: true });
        }
    });

    // 정렬된 행을 테이블에 다시 추가
    rows.forEach((row) => tableBody.appendChild(row));

    // 헤더 아이콘 업데이트
    updateSortIcons(table, header, isAscending);
}

function updateSortIcons(table, activeHeader, isAscending) {
    // 모든 헤더의 아이콘 초기화
    const allHeaders = table.querySelectorAll("th");
    allHeaders.forEach((header) => {
        const icon = header.querySelector(".sort-icon");
        if (icon) {
            icon.innerHTML = "&#8597;"; // 기본 화살표(내림차순)
        }
    });

    // 활성 헤더의 아이콘 업데이트
    const activeIcon = activeHeader.querySelector(".sort-icon");
    if (activeIcon) {
        activeIcon.innerHTML = isAscending ? "&uarr;" : "&darr;"; // 오름차순(▲), 내림차순(▼)
    }
}
function handleAttendanceFormSubmit() {
    // 입력 값 가져오기
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;
    const startTime = document.getElementById("startTime").value;
    const weekdays = Array.from(document.querySelectorAll('input[name="weekday"]:checked')).map((checkbox) => checkbox.value);

    // 데이터 검증
    if (!startDate || !endDate) {
        showReusableModal("시작 날짜와 종료 날짜를 모두 입력해주세요.");
        return;
    }
    if (new Date(startDate) > new Date(endDate)) {
        showReusableModal("종료 날짜는 시작 날짜보다 이후여야 합니다.");
        return;
    }
    if (!startTime) {
        showReusableModal("출근 시간을 입력해주세요.");
        return;
    }
    if (weekdays.length === 0) {
        showReusableModal("최소 하나의 요일을 선택해주세요.");
        return;
    }

    // 데이터 준비
    const data = {
        startDate: new Date(startDate).toISOString(),
        endDate: new Date(endDate).toISOString(),
        workStartTime: `${startDate}T${startTime}`, // 시작 날짜 + 시간 조합
        mon: weekdays.includes("월"),
        tue: weekdays.includes("화"),
        wed: weekdays.includes("수"),
        thu: weekdays.includes("목"),
        fri: weekdays.includes("금"),
        sat: weekdays.includes("토"),
        sun: weekdays.includes("일"),
    };

    // 서버로 데이터 전송
    fetch("/api/insert-work-time", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error(`서버 오류: ${response.status}`);
            }
            return response.text();
        })
        .then((message) => {
            showReusableModal(`${message}`);
            closeAttendanceModal();
        })
        .catch((error) => {
            showReusableModal(`오류 발생: ${error.message}`);
        });
}
let isEditMode = false;

document.querySelector(".btns.small-title:nth-child(3)").addEventListener("click", async () => {
    const teamCells = document.querySelectorAll(".team-cell");

    if (!isEditMode) {
        // 🟢 팀 번호를 input으로 변경
        teamCells.forEach((cell) => {
            const currentTeam = cell.textContent.trim().replace("팀", "");
            cell.innerHTML = `<input type="number" class="team-input" value="${currentTeam}" min="1" style="width: 60px;" />`;
        });
        isEditMode = true;
    } else {
        // 🔄 입력값을 읽어 서버에 전송하고 다시 텍스트로 변경
        const updates = [];

        teamCells.forEach((cell) => {
            const memberId = cell.getAttribute("data-member-id");
            const input = cell.querySelector("input");
            const newTeam = input.value;
            updates.push({ memberId, teamNum: newTeam });

            // <td> 내용 복원
            cell.textContent = `${newTeam}팀`;
        });

        try {
            const response = await fetch("/member/changeTeam", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ updates })  // ✅ 서버에서는 updates 배열 받도록 설정
            });

            if (!response.ok) throw new Error("팀 변경 실패");

            const result = await response.json();
            console.log("팀 전환 완료:", result);
            showReusableModal("팀 전환이 완료되었습니다.");

        } catch (error) {
            console.error("에러:", error);
            showReusableModal("팀 전환 중 오류가 발생했습니다.");
        }

        isEditMode = false;
    }
});