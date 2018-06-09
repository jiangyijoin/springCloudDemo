package ${baseProjectPackage}.controller;

import ${basePackage}.core.entity.Result;
import ${basePackage}.core.entity.ResultGenerator;
import ${basePackage}.core.web.BaseController;
import ${baseProjectPackage}.Model.${modelNameUpperCamel};
import ${baseProjectPackage}.service.${modelNameUpperCamel}Service;
import ${basePackage}.log.aop.LogSave;
import ${basePackage}.log.aop.methodType;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("${baseRequestMapping}")
@Api(value = "${modelNameLowerCamel}" ,tags = {"${modelNameLowerCamel}"})
public class ${modelNameUpperCamel}Controller extends BaseController {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/save")
	@ApiOperation(value = "save",notes = "方法描述")
    public Result save(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
	@ApiOperation(value = "delete",notes = "方法描述")
    public Result delete(@RequestParam String id) {
        ${modelNameLowerCamel}Service.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
	@ApiOperation(value = "update",notes = "方法描述")
    public Result update(${modelNameUpperCamel} ${modelNameLowerCamel}) {
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
	@ApiOperation(value = "detail",notes = "方法描述")
    public Result detail(@RequestParam String id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }

    @GetMapping("/queryList")
	@ApiOperation(value = "queryList",notes = "方法描述")
    public Result queryList() {
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/queryPage")
	@ApiOperation(value = "queryPage",notes = "方法描述")
    public Result queryPage(@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "0") Integer pageSize) {
        PageInfo<TagInfo> page = ${modelNameLowerCamel}Service.findPage(pageNum, pageSize);
        return ResultGenerator.genSuccessResult(page);
    }
}
