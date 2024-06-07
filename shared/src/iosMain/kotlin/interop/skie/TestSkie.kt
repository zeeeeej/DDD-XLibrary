package interop.skie

import co.touchlab.skie.configuration.annotations.DefaultArgumentInterop
import co.touchlab.skie.configuration.annotations.EnumInterop
import co.touchlab.skie.configuration.annotations.FlowInterop
import co.touchlab.skie.configuration.annotations.FunctionInterop
import co.touchlab.skie.configuration.annotations.SealedInterop
import co.touchlab.skie.configuration.annotations.SuppressSkieWarning
import co.touchlab.skie.configuration.annotations.SuspendInterop
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


//@SealedInterop.Enabled
@EnumInterop.Enabled
sealed class MyOp {
    object MyOp1 : MyOp()
    data class MyOp2(val name: String) : MyOp()
    data class MyOp3(val age: Int) : MyOp()
    data class MyOp4(val age: Int, val msg: String) : MyOp()
}

object TestSkieAtKotlin {

    val hello: String = "hello SKIE"

    var sealedMy: MySealed = MySealed.MySealed1
    var myOp: MyOp = MyOp.MyOp1

    @FlowInterop.Enabled
//@FlowInterop.Disabled
    fun myFlow(): Flow<Int> = flow {
        var i = 10
        while (i > 0) {
            delay(50)
            emit(i--)
        }
    }

    @EnumInterop.Enabled
//@EnumInterop.Disabled
    enum class MyEnum {
        A, B;
    }

    @EnumInterop.LegacyCaseName.Enabled
//@EnumInterop.LegacyCaseName.Disabled
    enum class MyEnum2 {
        A, B;
    }

    @SealedInterop.Enabled
    sealed interface MySealed {
        object MySealed1 : MySealed
        data class MySealed2(val name: String) : MySealed
    }

    @SealedInterop.EntireHierarchyExport.Enabled
    sealed interface MyEntireHierarchyExportSealed

    @SealedInterop.Function.Name("onEnum2")
    sealed interface HasCustomFunctionName

    @SealedInterop.Function.ArgumentLabel("of2")
    sealed interface HasCustomFunctionArgumentLabel

    @SealedInterop.Function.ParameterName("sealed2")
    sealed interface HasCustomFunctionParameterName

    @SealedInterop.ElseName("else2")
    sealed interface HasCustomElseName

    sealed interface SealedInterface {

        @SealedInterop.Case.Visible
        object Visible : SealedInterface

        @SealedInterop.Case.Hidden
        object Hidden : SealedInterface
    }

    sealed interface SealedInterface2 {

        @SealedInterop.Case.Name("CustomName2")
        object CustomName : SealedInterface2
    }

    @DefaultArgumentInterop.Enabled
    fun enabled(i: Int = 0) {
    }

    @DefaultArgumentInterop.MaximumDefaultArgumentCount(6)
    fun functionWithSixDefaultArguments(
        p1: Int = 0,
        p2: Int = 0,
        p3: Int = 0,
        p4: Int = 0,
        p5: Int = 0,
        p6: Int = 0,
    ) {
    }

    @FunctionInterop.FileScopeConversion.Enabled
    fun FileScopeConversionEnabled() {
    }

    @FunctionInterop.LegacyName.Enabled
    fun LegacyNameEnabled() {
    }

    @SuspendInterop.Enabled
    suspend fun suspendEnabled(): String {
        println("suspendEnabled ...")
        delay(1000)
        println("suspendEnabled delay 1000")
        delay(2000)
        println("suspendEnabled delay 2000")

        println("suspendEnabled z")
        return "hello suspend in skie"
    }

    @SuppressSkieWarning.NameCollision
    fun warningSuppressed() {
    }
}


