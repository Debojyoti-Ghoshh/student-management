// ==========================================
// BACKEND API
// ==========================================

const API = "http://localhost:8080/api";
let currentStudents = [];

// ==========================================
// SECTION NAVIGATION
// ==========================================

function showSection(sectionId) {

    document.querySelectorAll(".section")
        .forEach(section => {
            section.classList.remove("active");
        });

    document.getElementById(sectionId)
        .classList.add("active");


    // Load students whenever Students section opens
    if (sectionId === "students") {
        loadStudents();
    }


    // Load count whenever Dashboard opens
    if (sectionId === "dashboard") {
        loadStudentCount();
    }
}


// ==========================================
// LOAD ALL STUDENTS
// ==========================================

async function loadStudents() {

    try {

        const response =
            await fetch(`${API}/students`);


        if (!response.ok) {
            throw new Error("Failed to load students");
        }


        const students =
            await response.json();

        currentStudents = students;
        displayStudents(students);

        loadStudentCount();

    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to the backend.\n" +
            "Make sure Spring Boot is running."
        );
    }
}


// ==========================================
// DISPLAY STUDENTS IN TABLE
// ==========================================

function displayStudents(students) {

    const table =
        document.getElementById("studentTable");


    table.innerHTML = "";


    if (students.length === 0) {

        table.innerHTML = `
            <tr>
                <td colspan="8">
                    No students found.
                </td>
            </tr>
        `;

        return;
    }


    students.forEach((student,index) => {

        const row =
            document.createElement("tr");


        row.innerHTML = `

            <td>${index + 1}</td>

            <td>${student.id}</td>

            <td>${student.name}</td>

            <td>${student.rollNo}</td>

            <td>${student.department}</td>

            <td>${student.semester}</td>

            <td>${student.marks}</td>

            <td>

                <button
                    class="edit"
                    onclick="editStudent(${student.id})">
                    Edit
                </button>

                <button
                    class="delete"
                    onclick="deleteStudent(${student.id})">
                    Delete
                </button>

            </td>

        `;


        table.appendChild(row);

    });
}


// ==========================================
// ADD / UPDATE STUDENT
// ==========================================

async function saveStudent() {

    const id =
        document.getElementById("studentId").value;


    const name =
        document.getElementById("name").value.trim();


    const rollNo =
        document.getElementById("rollNo").value.trim();


    const department =
        document.getElementById("department").value.trim();


    const semester =
        document.getElementById("semester").value;


    const marks =
        document.getElementById("marks").value;


    // Basic validation

    if (
        !name ||
        !rollNo ||
        !department ||
        semester === "" ||
        marks === ""
    ) {

        alert("Please fill all student fields.");

        return;
    }


    const student = {

        name: name,

        rollNo: rollNo,

        department: department,

        semester: parseInt(semester),

        marks: parseFloat(marks)

    };


    try {

        let response;


        // UPDATE
        if (id) {

            response =
                await fetch(
                    `${API}/students/${id}`,
                    {
                        method: "PUT",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(student)
                    }
                );

        }

        // ADD
        else {

            response =
                await fetch(
                    `${API}/students`,
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(student)
                    }
                );

        }


        if (!response.ok) {

            const errorText =
                await response.text();

            console.error(errorText);

            alert(
                "Unable to save student.\n" +
                "Check whether the Roll Number already exists."
            );

            return;
        }


        if (id) {

            alert(
                "Student updated successfully!"
            );

        } else {

            alert(
                "Student added successfully!"
            );
        }


        clearForm();

        await loadStudents();

    } catch (error) {

        console.error(error);

        alert(
            "Server error. Make sure Spring Boot is running."
        );
    }
}


// ==========================================
// EDIT STUDENT
// ==========================================

async function editStudent(id) {

    try {

        const response =
            await fetch(
                `${API}/students/${id}`
            );


        if (!response.ok) {
            throw new Error(
                "Student not found"
            );
        }


        const student =
            await response.json();


        document.getElementById(
            "studentId"
        ).value = student.id;


        document.getElementById(
            "name"
        ).value = student.name;


        document.getElementById(
            "rollNo"
        ).value = student.rollNo;


        document.getElementById(
            "department"
        ).value = student.department;


        document.getElementById(
            "semester"
        ).value = student.semester;


        document.getElementById(
            "marks"
        ).value = student.marks;


        showSection("students");


        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });


    } catch (error) {

        console.error(error);

        alert(
            "Unable to load student."
        );
    }
}


// ==========================================
// DELETE STUDENT
// ==========================================

async function deleteStudent(id) {

    const confirmed =
        confirm(
            "Are you sure you want to delete this student?"
        );


    if (!confirmed) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API}/students/${id}`,
                {
                    method: "DELETE"
                }
            );


        if (!response.ok) {

            alert(
                "Failed to delete student."
            );

            return;
        }


        alert(
            "Student deleted successfully!"
        );


        await loadStudents();


    } catch (error) {

        console.error(error);

        alert(
            "Server error."
        );
    }
}


// ==========================================
// SEARCH STUDENTS
// ==========================================

async function searchStudents() {

    const name =
        document.getElementById(
            "searchInput"
        ).value.trim();


    if (!name) {

        await loadStudents();

        return;
    }


    try {

        const response =
            await fetch(
                `${API}/students/search?name=${encodeURIComponent(name)}`
            );


        if (!response.ok) {

            throw new Error(
                "Search failed"
            );
        }


        const students =
            await response.json();
        
        currentStudents = students;

        displayStudents(students);


    } catch (error) {

        console.error(error);

        alert(
            "Search failed."
        );
    }
}






// ==========================================
// SORT STUDENTS
// ==========================================

function sortStudents() {

    const sortType =
        document.getElementById("sortStudents").value;

    let sortedStudents =
        [...currentStudents];


    switch (sortType) {

        case "idAsc":

            sortedStudents.sort(
                (a, b) => a.id - b.id
            );

            break;


        case "idDesc":

            sortedStudents.sort(
                (a, b) => b.id - a.id
            );

            break;


        case "nameAsc":

            sortedStudents.sort(
                (a, b) =>
                    a.name.localeCompare(
                        b.name,
                        undefined,
                        { sensitivity: "base" }
                    )
            );

            break;


        case "nameDesc":

            sortedStudents.sort(
                (a, b) =>
                    b.name.localeCompare(
                        a.name,
                        undefined,
                        { sensitivity: "base" }
                    )
            );

            break;


        case "default":

            break;
    }


    displayStudents(sortedStudents);
}


// ==========================================
// CLEAR STUDENT FORM
// ==========================================

function clearForm() {

    document.getElementById(
        "studentId"
    ).value = "";


    document.getElementById(
        "name"
    ).value = "";


    document.getElementById(
        "rollNo"
    ).value = "";


    document.getElementById(
        "department"
    ).value = "";


    document.getElementById(
        "semester"
    ).value = "";


    document.getElementById(
        "marks"
    ).value = "";
}


// ==========================================
// GET TOTAL STUDENT COUNT
// ==========================================

async function loadStudentCount() {

    try {

        const response =
            await fetch(
                `${API}/students/count`
            );


        if (!response.ok) {

            throw new Error(
                "Failed to get count"
            );
        }


        const count =
            await response.json();


        document.getElementById(
            "studentCount"
        ).textContent = count;


    } catch (error) {

        console.error(error);

        document.getElementById(
            "studentCount"
        ).textContent = "0";
    }
}


// ==========================================
// CALCULATOR
// ==========================================

async function calculate() {

    const a =
        document.getElementById(
            "num1"
        ).value;


    const b =
        document.getElementById(
            "num2"
        ).value;


    const operation =
        document.getElementById(
            "operation"
        ).value;


    if (a === "" || b === "") {

        alert(
            "Please enter both numbers."
        );

        return;
    }


    try {

        const response =
            await fetch(
                `${API}/calculator?a=${encodeURIComponent(a)}&b=${encodeURIComponent(b)}&operation=${operation}`
            );


        if (!response.ok) {

            const errorText =
                await response.text();

            console.error(errorText);

            alert(
                "Calculation failed."
            );

            return;
        }


        const data =
            await response.json();


        document.getElementById(
            "calcResult"
        ).textContent =
            "Result: " + data.result;


    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to calculator backend."
        );
    }
}


// ==========================================
// INITIAL PAGE LOAD
// ==========================================

window.onload = function () {

    loadStudentCount();

};