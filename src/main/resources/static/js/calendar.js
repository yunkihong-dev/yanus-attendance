// JavaScript for generating the calendar and handling date selection
const monthYearSpan = document.getElementById('month-year');
const calendarBody = document.querySelector('.calendar-body');
const selectedDateInput = document.getElementById('selected-date');

let currentDate = new Date();
let selectedDate;

function generateCalendar() {
    calendarBody.innerHTML = ''; // Clear the previous calendar
    const month = currentDate.getMonth();
    const year = currentDate.getFullYear();

    // Set the month and year title
    monthYearSpan.textContent = currentDate.toLocaleString('default', { month: 'long' }) + ' ' + year;

    // Calculate the first day of the month
    const firstDay = new Date(year, month, 1);
    const daysInMonth = new Date(year, month + 1, 0).getDate();

    // Fill in the days
    for (let i = 1; i <= daysInMonth; i++) {
        const day = document.createElement('div');
        day.textContent = i;
        if (i === new Date().getDate() && month === new Date().getMonth()) {
            // Highlight today
            day.classList.add('today');
        }
        day.addEventListener('click', () => selectDate(i, day));
        calendarBody.appendChild(day);
    }
}

function selectDate(day, element) {
    if (selectedDate) {
        selectedDate.classList.remove('selected');
    }
    selectedDate = element;
    element.classList.add('selected');
    selectedDateInput.value = `${currentDate.getFullYear()}-${currentDate.getMonth() + 1}-${day}`;
}

// Event listeners for the previous and next buttons
document.getElementById('prev-month').addEventListener('click', () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    generateCalendar();
});

document.getElementById('next-month').addEventListener('click', () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    generateCalendar();
});

// Generate the initial calendar
generateCalendar();