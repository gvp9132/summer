/** 角色信息表数据类型声明 */
export type SecurityRole = {
    /** 自增主键 */
    id: number;
    /** 数据唯一标识 */
    key: string;
    /** 角色定义 */
    authority: string;
    /** 角色图标 */
    icon: string;
    /** 角色说明 */
    explain: string;
    /** 角色权重 */
    weight: number;
    /** 创建时间 */
    createTime: Date;
    /** 修改时间 */
    updateTime: Date;
    /** 逻辑删除字段 */
    delete: boolean;
    /** 数据版本 */
    version: number;
    /** 数据排序字段 */
    order: number;
    /** 数据备注信息 */
    remark: string;
}
export default securityRoleColumns;
