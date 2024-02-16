async function downloadExcel1() {
    let startDateInput = document.getElementById("startDate");
    let endDateInput = document.getElementById("endDate");

    let dateFromPage = {
        startDate: startDateInput.value,
        endDate: endDateInput.value,
    };

    console.log(dateFromPage);

    try {
        let excelBlob = await createExcel(dateFromPage,'1');
        downloadBlob(excelBlob, 'report.xlsx');
    } catch (error) {
        console.error(error);
    }
}async function downloadExcel2() {
    let startDateInput = document.getElementById("startDate");
    let endDateInput = document.getElementById("endDate");

    let dateFromPage = {
        startDate: startDateInput.value,
        endDate: endDateInput.value,
    };

    console.log(dateFromPage);

    try {
        let excelBlob = await createExcel(dateFromPage,'2');
        downloadBlob(excelBlob, 'report.xlsx');
    } catch (error) {
        console.error(error);
    }
}

async function createExcel(dateFromPage,url) {
    let response = await fetch('/admin/stock/excel/'+url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(dateFromPage),
    });

    if (response.ok) {
        return await response.blob();
    } else {
        console.error('Error creating the Excel file');
        throw new Error('Error creating the Excel file');
    }
}

function downloadBlob(blob, fileName) {
    let link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = fileName;

    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}
