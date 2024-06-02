import { createAsyncThunk } from "@reduxjs/toolkit";
import Request from "http/Request";
import {PagingData, PagingResult} from "type/Page";
import {SecurityMenu} from "type/entity/SecurityMenu";

/** 请求菜单信息分页数据 */
export const fetchSecurityMenuPageData = createAsyncThunk(
    'securityMenu/fetchSecurityMenuPageData',
    async (paging: PagingData) => {
        return await Request.get<PagingResult<SecurityMenu>>("/manager/admin/security_menu/page/" + paging.page + "/" + paging.pageSize);
    }
);

/** 添加菜单信息数据 */
export const fetchAddSecurityMenu = createAsyncThunk(
    'securityMenu/fetchAddSecurityMenu',
    async (vo: SecurityMenu) => {
       return await Request.put<number>("/manager/admin/security_menu", vo);
    }
);

/** 更新菜单信息数据 */
export const fetchUpdateSecurityMenu = createAsyncThunk(
    'securityMenu/fetchUpdateSecurityMenu',
    async (vo: SecurityMenu) => {
        return await Request.post<number>("/manager/admin/security_menu", vo);
    }
);

/** 删除菜单信息数据 */
export const fetchDeleteSecurityMenu = createAsyncThunk(
    'securityMenu/fetchDeleteSecurityMenu',
    async (id: number) => {
        return await Request.delete<number>("/manager/admin/security_menu" + id);
    }
);

