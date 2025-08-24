// 전역 변수
let darkModeToggle = document.getElementById("darkModeToggle");
let isEditMode = false;

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

// 페이지 로드 시 초기화
window.onload = function() {
    const darkModeSetting = localStorage.getItem('darkMode');
    if (darkModeSetting === 'enabled') {
        document.body.classList.add('dark-mode');
        darkModeToggle.checked = true;
    }
}

// 체크박스 전체 선택/해제
function selectAll(selectAll) {
    const checkboxes = document.getElementsByName('members');
    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    });
}

function selectAllNoWork(selectAll) {
    const checkboxes = document.getElementsByName('noWorks');
    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    });
}

// 로그아웃 이벤트
document.getElementById("log-out").addEventListener('click', () => {
    showConfirmModal("로그아웃 하시겠습니까?").then(res => {
        if (res) {
            window.location.href = '/member/logout';
        }
    });
});

// 로그아웃 텍스트 호버 효과
document.getElementById('logout-text').addEventListener('mouseover', function() {
    this.textContent = '로그아웃';
});

document.getElementById('logout-text').addEventListener('mouseout', function() {
    this.textContent = member.memberName + ' 관리자님, 반갑습니다!';
});

// 더보기 기능
function showMore() {
    const hiddenRows = Array.from(document.querySelectorAll('.hidden-member'));
    const nextRows = hiddenRows.slice(0, 5);
    
    nextRows.forEach(function(row) {
        row.classList.remove('hidden-member');
    });

    if (hiddenRows.length <= 5) {
        document.getElementById('show-more-btn').style.display = 'none';
    }
}

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

// 미출근 사유 승인
function OkToNoWork() {
    const selectedItems = Array.from(document.querySelectorAll('input[type="checkbox"][name="noWorks"]:checked'))
        .map(checkbox => checkbox.value);

    if (selectedItems[0] === 'selectAll') {
        selectedItems.shift();
    }
    
    if (selectedItems.length === 0) {
        showReusableModal("한개 이상 선택해주세요");
        return;
    }

    fetch('/api/NoWorkOk', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedItems),
    })
    .then(response => response.text())
    .then(data => {
        showReusableModal(data);
        selectedItems.forEach(value => {
            const element = document.getElementById(value);
            if (element) {
                element.innerText = "승인";
                element.className = "status-badge approved";
            }
        });
    })
    .catch(error => {
        showReusableModal('Error: ' + error);
    });
}

// 미출근 사유 거절
function NotOkToNoWork() {
    const selectedItems = Array.from(document.querySelectorAll('input[type="checkbox"][name="noWorks"]:checked'))
        .map(checkbox => checkbox.value);

    if (selectedItems[0] === 'selectAll') {
        selectedItems.shift();
    }
    
    if (selectedItems.length === 0) {
        showReusableModal("한개 이상 선택해주세요");
        return;
    }

    fetch('/api/NoWorkNotOk', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(selectedItems),
    })
    .then(response => response.text())
    .then(data => {
        showReusableModal(data);
        selectedItems.forEach(value => {
            const element = document.getElementById(value);
            if (element) {
                element.innerText = "거절";
                element.className = "status-badge rejected";
            }
        });
    })
    .catch(error => {
        showReusableModal('Error: ' + error);
    });
}

// 다크 모드 토글
darkModeToggle.addEventListener('change', function() {
    document.body.classList.toggle('dark-mode');

    if (document.body.classList.contains('dark-mode')) {
        localStorage.setItem('darkMode', 'enabled');
    } else {
        localStorage.setItem('darkMode', 'disabled');
    }
});

// 드롭다운 및 폼 관리
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

    // 드롭다운 토글
    toggleButton.addEventListener("click", (event) => {
        event.stopPropagation();
        dropdown.classList.toggle("active");
    });

    // 옵션 선택/해제
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
    deselectAllButton.addEventListener("click", () => {
        options.forEach(o => o.classList.remove("selected"));
        selectedMembers = [];
    });

    // 드롭다운 닫기
    closeButton.addEventListener("click", () => {
        dropdown.classList.remove("active");
    });

    // 오늘 버튼
    document.getElementById("todayButton").addEventListener("click", () => {
        const today = new Date().toISOString().split('T')[0];
        dateStart.value = today;
        dateEnd.value = today;
    });

    // 폼 제출
    form.addEventListener("submit", (event) => {
        event.preventDefault();

        // 날짜 유효성 검사
        if (!dateStart.value || !dateEnd.value) {
            showReusableModal("시작일과 종료일을 모두 선택해주세요.");
            return;
        }

        if (dateStart.value > dateEnd.value) {
            showReusableModal("종료일은 시작일보다 이후여야 합니다.");
            return;
        }

        // 팀원 선택 유효성 검사
        if (selectedMembers.length === 0) {
            showReusableModal("최소 한 명의 팀원을 선택해주세요.");
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

// 회원 삭제
document.getElementById('delete-member-btn').addEventListener('click', async () => {
    const memberIds = Array.from(document.querySelectorAll('input[type="checkbox"][name="members"]:checked'))
        .map(checkbox => checkbox.value);

    if (memberIds.length === 0) {
        showReusableModal("삭제할 회원을 선택하세요.");
        return;
    }

    const confirmed = await showConfirmModal("선택한 회원들을 삭제하시겠습니까?");
    if (!confirmed) return;

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
            }, 1000);
        } else {
            showReusableModal(result.message || "회원 삭제에 실패했습니다.");
        }
    } catch (error) {
        console.error("삭제 중 오류 발생", error);
        showReusableModal("회원 삭제 중 오류가 발생했습니다.");
    }
});

// 모달 관리
function openAttendanceModal() {
    const modal = document.getElementById("attendance-modal");
    modal.style.display = "grid";
}

function closeAttendanceModal() {
    const modal = document.getElementById("attendance-modal");
    modal.style.display = "none";
}

// 출근시간 폼 제출
function handleAttendanceFormSubmit() {
    const startDate = document.getElementById("startDate").value;
    const endDate = document.getElementById("endDate").value;
    const startTime = document.getElementById("startTime").value;
    const weekdays = Array.from(document.querySelectorAll('input[name="weekday"]:checked'))
        .map((checkbox) => checkbox.value);

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
        workStartTime: `${startDate}T${startTime}`,
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
        showReusableModal(message);
        closeAttendanceModal();
    })
    .catch((error) => {
        showReusableModal(`오류 발생: ${error.message}`);
    });
}

// 테이블 정렬
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
            icon.innerHTML = "&#8597;";
        }
    });

    // 활성 헤더의 아이콘 업데이트
    const activeIcon = activeHeader.querySelector(".sort-icon");
    if (activeIcon) {
        activeIcon.innerHTML = isAscending ? "&uarr;" : "&darr;";
    }
}

// 팀 전환 기능
document.querySelector(".btn.btn-info").addEventListener("click", async () => {
    const teamCells = document.querySelectorAll(".team-cell");

    if (!isEditMode) {
        // 팀 번호를 input으로 변경
        teamCells.forEach((cell) => {
            const currentTeam = cell.textContent.trim().replace("팀", "");
            cell.innerHTML = `<input type="number" class="team-input" value="${currentTeam}" min="1" style="width: 60px;" />`;
        });
        isEditMode = true;
    } else {
        // 입력값을 읽어 서버에 전송하고 다시 텍스트로 변경
        const updates = [];

        teamCells.forEach((cell) => {
            const memberId = cell.getAttribute("data-member-id");
            const input = cell.querySelector("input");
            const newTeam = input.value;
            updates.push({ memberId, teamNum: newTeam });

            // 셀 내용 복원
            cell.textContent = `${newTeam}팀`;
        });

        try {
            const response = await fetch("/member/changeTeam", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ updates })
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

// 키보드 이벤트 (ESC로 모달 닫기)
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        const modals = document.querySelectorAll('.confirm-modal, .alert-modal, .attendance-modal');
        modals.forEach(modal => {
            if (modal.style.display !== 'none') {
                modal.style.display = 'none';
            }
        });
    }
});