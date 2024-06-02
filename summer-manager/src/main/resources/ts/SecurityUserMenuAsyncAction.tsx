import { createAsyncThunk } from "@reduxjs/toolkit";
import Request from "http/Request";
import {PagingData, PagingResult} from "type/Page";
import {SecurityUserMenu} from "type/entity/SecurityUserMenu";

/** 请求用户菜单关系表分页数据 */
export const fetchSecurityUserMenuPageData = createAsyncThunk(
    'securityUserMenu/fetchSecurityUserMenuPageData',
    async (paging: PagingData) => {
        return await Request.get<PagingResult<SecurityUserMenu>>("/manager/admin/security_user_menu/page/" + paging.page + "/" + paging.pageSize);
    }
);

/** 添加用户菜单关系表数据 */
export const fetchAddSecurityUserMenu = createAsyncThunk(
    'securityUserMenu/fetchAddSecurityUserMenu',
    async (vo: SecurityUserMenu) => {
       return await Request.put<number>("/manager/admin/security_user_menu", vo);
    }
);

/** 更新用户菜单关系表数据 */
export const fetchUpdateSecurityUserMenu = createAsyncThunk(
    'securityUserMenu/fetchUpdateSecurityUserMenu',
    async (vo: SecurityUserMenu) => {
        return await Request.post<number>("/manager/admin/security_user_menu", vo);
    }
);

/** 删除用户菜单关系表数据 */
export const fetchDeleteSecurityUserMenu = createAsyncThunk(
    'securityUserMenu/fetchDeleteSecurityUserMenu',
    async (id: number) => {
        return await Request.delete<number>("/manager/admin/security_user_menu" + id);
    }
);

