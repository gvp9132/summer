import { createAsyncThunk } from "@reduxjs/toolkit";
import Request from "http/Request";
import {PagingData, PagingResult} from "type/Page";
import {SecurityRole} from "type/entity/SecurityRole";

/** 请求角色信息表分页数据 */
export const fetchSecurityRolePageData = createAsyncThunk(
    'securityRole/fetchSecurityRolePageData',
    async (paging: PagingData) => {
        return await Request.get<PagingResult<SecurityRole>>("/manager/admin/security_role/page/" + paging.page + "/" + paging.pageSize);
    }
);

/** 添加角色信息表数据 */
export const fetchAddSecurityRole = createAsyncThunk(
    'securityRole/fetchAddSecurityRole',
    async (vo: SecurityRole) => {
       return await Request.put<number>("/manager/admin/security_role", vo);
    }
);

/** 更新角色信息表数据 */
export const fetchUpdateSecurityRole = createAsyncThunk(
    'securityRole/fetchUpdateSecurityRole',
    async (vo: SecurityRole) => {
        return await Request.post<number>("/manager/admin/security_role", vo);
    }
);

/** 删除角色信息表数据 */
export const fetchDeleteSecurityRole = createAsyncThunk(
    'securityRole/fetchDeleteSecurityRole',
    async (id: number) => {
        return await Request.delete<number>("/manager/admin/security_role" + id);
    }
);

