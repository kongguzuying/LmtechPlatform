package com.lmtech.admin.common.tags;

import com.lmtech.common.PageData;
import com.lmtech.util.StringUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by huang.jb on 2016-7-12.
 */
public class PagerTag extends TagSupport {
	private String url; // 请求URI
	private PageData pager; // 分页对象
	private int btnSize = 5; // 分页按钮数

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		// 拼写要输出到页面的HTML文本
		StringBuilder sb = new StringBuilder();
		if (null == this.pager) {
			sb.append("<strong>没有可显示的项目</strong>\r\n");
		} else {

			int pageCount = pager.getTotalPage();
			int pageNo = pager.getCurrentPageNumber();
			int pageSize = pager.getPageSize();

			if (pager.getTotal() == 0) {
				sb.append("<strong>没有可显示的项目</strong>\r\n");
			} else if (pager.getTotalPage() <= 1) {
				// 只有一页的情况下，不显示分页标签
			} else {
				// 设置分页信息
				int sizeStart = (pageNo - 1) * pageSize + 1;
				int sizeEnd = (pageNo * pageSize);
				if (sizeEnd > pager.getTotal()) {
					sizeEnd = pager.getTotal();
				}
				sb.append(String
						.format("<div class=\"pagertext\">显示第%1$d-%2$d条 共%3$d条数据 共%4$d页</div>",
								sizeStart, sizeEnd, pager.getTotal(), pageCount));
				// 把当前页号设置成请求参数
				sb.append(
						"<input type=\"hidden\" id=\"pageIndex\" name=\"pageIndex\" value=\"")
						.append(pageNo).append("\"/>\r\n");
				sb.append("<div class=\"dataTables_paginate paging_bootstrap pagination\"><ul>");
				// 页号越界处理
				if (pageNo > pageCount) {
					pageNo = pageCount;
				}
				if (pageNo < 1) {
					pageNo = 1;
				}

				// 首页处理
				if (pageNo == 1) {
					sb.append("<li  class=\"disabled\"><a href=\"#\">首页</a>")
							.append("</li>\r\n");
				} else {
					sb.append("<li><a href=\"").append(this.getHref(1))
							.append("\">首页</a></li>\r\n");
				}

				// 上一页处理
				if (pageNo == 1) {
					sb.append("<li  class=\"disabled\"><a href=\"#\">上一页</a>")
							.append("</li>\r\n");
				} else {
					sb.append("<li><a href=\"")
							.append(this.getHref(pageNo - 1))
							.append("\">上一页</a></li>\r\n");
				}

				// 显示当前页附近的页
				int halfBtnSize = (btnSize / 2);
				int start = pageNo > halfBtnSize ? (pageNo - halfBtnSize) : 1;
				int end = pageNo + halfBtnSize < pageCount ? (pageNo + halfBtnSize)
						: pageCount;
				// 处理首尾页按钮不够的情况
				int curBtnSize = end - start + 1;
				if (curBtnSize < btnSize) {
					if (start <= halfBtnSize
							&& end + (btnSize - curBtnSize) <= pageCount) {
						end = end + (btnSize - curBtnSize);
					} else if ((end > pageCount - halfBtnSize && end <= pageCount)
							&& start - (btnSize - curBtnSize) >= pageCount) {
						start = start - (btnSize - curBtnSize);
					}
				}
				for (int i = start; i <= end; i++) {
					if (pageNo == i) { // 当前页号不需要超链接
						sb.append("<li class=\"active\"><a>").append(i)
								.append("</a></li>\r\n");
					} else {
						sb.append("<li><a href=\"").append(this.getHref(i))
								.append("\">").append(i)
								.append("</a></li>\r\n");
					}
				}

				// 下一页处理
				if (pageNo == pageCount) {
					sb.append("<li  class=\"disabled\"><a href=\"#\">下一页</a>")
							.append("</li>\r\n");
				} else {
					sb.append("<li><a href=\"")
							.append(this.getHref(pageNo + 1))
							.append("\">下一页</a></li>\r\n");
				}

				// 尾页处理
				if (pageNo == pageCount) {
					sb.append("<li  class=\"disabled\"><a href=\"#\">尾页</a>")
							.append("</li>\r\n");
				} else {
					sb.append("<li><a href=\"").append(this.getHref(pageCount))
							.append("\">尾页</a></li>\r\n");
				}
			}
		}
		// 把生成的HTML输出到响应中
		try {
			pageContext.getOut().println(sb.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY; // 本标签主体为空,所以直接跳过主体
	}

	private String getHref(int pageNo) {
		if (!StringUtil.isNullOrEmpty(url)) {
			if (url.indexOf("?") > 0) {
				return url + "&pageIndex=" + pageNo;
			} else {
				return url + "?pageIndex=" + pageNo;
			}
		} else {
			return "javascript:onPageClick(" + pageNo + ")";
		}
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setBtnSize(int btnSize) {
		this.btnSize = btnSize;
	}

	public void setPager(PageData pager) {
		this.pager = pager;
	}
}
