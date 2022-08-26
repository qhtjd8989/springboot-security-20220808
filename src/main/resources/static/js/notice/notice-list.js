const searchButton = document.querySelector(".search-button");

searchButton.onclick = () => {
	load(1);
}

let nowPage = 1;

load(nowPage)

function load(nowPage) {
	const searchFlag = document.querySelector(".search-select").value;
	const searchValue = document.querySelector(".search-input").value;
	
	$.ajax({
		async: false,
		type: "get",
		url: "/api/v1/notice/list/" + nowPage,
		data: {
			"searchFlag": searchFlag,
			"searchValue": searchValue
		},
		dataType: "json",
		success: (response) => {
			getList(response.data);
			getPageNumbers(response.data.totalNoticeCount);
		},
		error: (error) => {
			console.log(error);
		}
		
	});
	
}

function getList(list){
	const tbody = document.querySelector("tbody");
	tbody.innerHTML = "";
	
	list.forEach(notice => {
		tbody.innerHTML += `
			<tr class="notice-row">
                <td>${notice.noticeCode}</td>
                <td>${notice.noticeTitle}</td>
                <td>${notice.userId}</td>
                <td>${notice.createDate}</td>
                <td>${notice.noticeCount}</td>
            </tr>
		`;
	});
}

function getPageNumbers(totalNoticeCount) {
	const pageButtons = document.querySelector(".page-buttons");
	
	const totalPageCount = totalNoticeCount % 10 == 0 ? totalNoticeCount / 10 : (totalNoticeCount / 10) + 1;
	
	const startIndex = nowPage % 5 == 0? nowPage - 4 : nowPage - (nowPage % 5) + 1;
	const endIndex = startIndex + 4 <= totalPageCount ? startIndex + 4 : totalPageCount;
}

function getWriteButton() {
	const listFooter = document.querySelector(".list-footer");
	
	if(getUser() != null) {
		if(getUser().userRoles.includes("ROLE_ADMIN")) {
			listFooter.innerHTML += `
				<button type="button" class="notice-add-button">글 쓰기</button>
			`;
			
			const noticeAddButton = document.querySelector(".notice-add-button");
			
			noticeAddButton.onclick = () => {
				location.href = "/notice/addition";
			}
		}
	}
}

getWriteButton();