/** 用户角色关系表数据类型声明 */
export type SecurityUserRole = {
    /** 自增主建 */
    id: number;
    /** 此数据唯一识别码 */
    key: string;
    /** 用户表id */
    userId: number;
    /** 角色表id */
    roleId: number;
    /** 创建时间 */
    createTime: Date;
    /** 数据版本 */
    version: number;
    /** 逻辑删除字段 */
    delete: boolean;
    /** 备注信息 */
    remark: string;
}
export default securityUserRoleColumns;
