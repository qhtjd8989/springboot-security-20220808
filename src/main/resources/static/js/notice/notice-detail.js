const preButton = document.querySelector(".pre");
const nextButton = document.querySelector(".next");

let noticeCode = location.pathname.substring(location.pathname.lastIndexOf("/") + 1);

load("/api/v1/notice/");

function load(uri) {
    $.ajax({
        async: false,
        type: "get",
        url: uri + noticeCode,
        datatype: "json",
        success: (response) => {
			console.log(JSON.stringify(response.data))
            getNotice(response.data);
        },
        error: (error) => {
            console.log(error);
        }
    })
}

function getNotice(notice) {
    const noticeDetailTitle = document.querySelector(".notice-detail-title");
    const noticeDatailDescription = document.querySelectorAll(".notice-datail-description h3");
    const noticeContent = document.querySelector(".notice-content");
    const noticeFile = document.querySelector(".notice-file");
    
    noticeCode = notice.noticeCode;
    
    noticeDetailTitle.innerHTML = notice.noticeTitle;
    noticeDatailDescription[0].innerHTML = "작성자: " + notice.userId;
    noticeDatailDescription[1].innerHTML = "작성일: " + notice.createDate;
    noticeDatailDescription[2].innerHTML = "조회수: " + notice.noticeCount;
    noticeContent.innerHTML = notice.noticeContent;
    
    noticeFile.innerHTML = "<h3>첨부파일:</h3>";
    let noticeFileArray = new Array(); 
    
    notice.downloadFiles.forEach(file => {
		noticeFileArray.push(`<a href="/api/v1/notice/file/${file.fileCode}">${file.fileName}</a>`)
	});
	noticeFile.innerHTML += noticeFileArray.join(" / ");
}

preButton.onclick = () => {
	load("/api/v1/notice/pre/");
}

nextButton.onclick = () => {
	load("/api/v1/notice/next/");
}
