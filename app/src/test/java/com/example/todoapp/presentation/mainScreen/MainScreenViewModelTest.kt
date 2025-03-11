package com.example.todoapp.presentation.mainScreen

import com.example.todoapp.R
import com.example.todoapp.data.repositories.MotherObjectRep
import com.example.todoapp.data.repositories.TaskRepository
import com.example.todoapp.utils.ResourceProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainScreenViewModelTest {

    private lateinit var mainScreenViewModel: MainScreenViewModel

    @MockK
    private lateinit var taskRepository: TaskRepository

    @MockK
    private lateinit var resourceProvider: ResourceProvider

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(testDispatcher)
        coEvery { taskRepository.getTasksColor() } returns flowOf(emptyList())
        coEvery { taskRepository.getCategoriesCountTask() } returns flowOf(emptyList())

        mainScreenViewModel = MainScreenViewModel(taskRepository,resourceProvider)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `createTask should update message and isLoading state`() = runTest {
        // Given
        val taskModel = MotherObjectRep.taskModel
        val expectedMessage = "Se ha creado una nueva tarea"

        coEvery { resourceProvider.getString(R.string.newTaskCreatedMsg) } returns expectedMessage
        coEvery { taskRepository.createTask(taskModel) } returns Unit

        // When
        mainScreenViewModel.createTask(taskModel)
        mainScreenViewModel.resetMessages()
        testDispatcher.scheduler.advanceUntilIdle()


        // Then
        coVerify(exactly = 1) { taskRepository.createTask(taskModel) }
        assert(mainScreenViewModel.message.first() == expectedMessage)
        assert(mainScreenViewModel.isLoading.first() == false)
    }

    @Test
    fun `createTask should handle exception and update message`() = runTest {
        // Given
        val taskModel = MotherObjectRep.taskModel
        val exceptionMessage = "Error creating task"
        coEvery { taskRepository.createTask(taskModel) } throws Exception(exceptionMessage)

        // When
        mainScreenViewModel.createTask(taskModel)
        mainScreenViewModel.resetMessages()

        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { taskRepository.createTask(taskModel) }
        assert(mainScreenViewModel.message.first() == exceptionMessage)
        assert(mainScreenViewModel.isLoading.first() == false) // Ahora pasa
    }

    @Test
    fun `updateTask should update message and isLoading state`() = runTest {
        // Given
        val taskModel = MotherObjectRep.taskModel
        coEvery { taskRepository.updateTask(taskModel) } returns Unit

        // When
        mainScreenViewModel.updateTask(taskModel)
        mainScreenViewModel.resetMessages()
        testDispatcher.scheduler.advanceUntilIdle()


        // Then
        coVerify(exactly = 1) { taskRepository.updateTask(taskModel) }
        assert(mainScreenViewModel.message.first() == null)
        assert(mainScreenViewModel.isLoading.first() == false) // Ahora pasa
    }

    @Test
    fun `updateTask should handle exception and update message`() = runTest {
        // Given
        val taskModel = MotherObjectRep.taskModel
        val exceptionMessage = "Error creating task"
        coEvery { taskRepository.updateTask(taskModel) } throws Exception(exceptionMessage)

        // When
        mainScreenViewModel.updateTask(taskModel)
        mainScreenViewModel.resetMessages()

        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { taskRepository.updateTask(taskModel) }
        assert(mainScreenViewModel.message.first() == exceptionMessage)
        assert(mainScreenViewModel.isLoading.first() == false) // Ahora pasa
    }

    @Test
    fun `deleteTask should update message and isLoading state`() = runTest {
        // Given
        val taskModel = MotherObjectRep.taskModel
        val exceptionMessage = "Se ha eliminado una tarea"

        coEvery { resourceProvider.getString(R.string.newTaskDeletedMsg) } returns exceptionMessage
        coEvery { taskRepository.deleteTask(taskModel) } returns Unit

        // When
        mainScreenViewModel.deleteTask(taskModel)
        mainScreenViewModel.resetMessages()
        testDispatcher.scheduler.advanceUntilIdle()


        // Then
        coVerify(exactly = 1) { taskRepository.deleteTask(taskModel) }
        assert(mainScreenViewModel.message.first() == exceptionMessage)
        assert(mainScreenViewModel.isLoading.first() == false)
    }

    @Test
    fun `deleteTask should handle exception and update message`() = runTest {
        // Given
        val taskModel = MotherObjectRep.taskModel
        val exceptionMessage = "Error creating task"
        coEvery { taskRepository.deleteTask(taskModel) } throws Exception(exceptionMessage)

        // When
        mainScreenViewModel.deleteTask(taskModel)
        mainScreenViewModel.resetMessages()

        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { taskRepository.deleteTask(taskModel) }
        assert(mainScreenViewModel.message.first() == exceptionMessage)
        assert(mainScreenViewModel.isLoading.first() == false) // Ahora pasa
    }

    @Test
    fun `createCategory should update message and isLoading state`() = runTest {
        // Given
        val categoryModel = MotherObjectRep.categoryModel
        val expectedMessage = "Se ha creado una categoría"

        coEvery { resourceProvider.getString(R.string.newCategoryCreatedMsg) } returns expectedMessage
        coEvery { taskRepository.createCategory(categoryModel) } returns Unit

        // When
        mainScreenViewModel.createCategory(categoryModel)
        mainScreenViewModel.resetMessages()
        testDispatcher.scheduler.advanceUntilIdle()


        // Then
        coVerify(exactly = 1) { taskRepository.createCategory(categoryModel) }
        assert(mainScreenViewModel.message.first() == expectedMessage)
        assert(mainScreenViewModel.isLoading.first() == false)
    }

    @Test
    fun `createCategory should handle exception and update message`() = runTest {
        // Given
        val categoryModel = MotherObjectRep.categoryModel
        val exceptionMessage = "Error creating task"
        coEvery { taskRepository.createCategory(categoryModel) } throws Exception(exceptionMessage)

        // When
        mainScreenViewModel.createCategory(categoryModel)
        mainScreenViewModel.resetMessages()

        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { taskRepository.createCategory(categoryModel) }
        assert(mainScreenViewModel.message.first() == exceptionMessage)
        assert(mainScreenViewModel.isLoading.first() == false) // Ahora pasa
    }

    @Test
    fun `updateCategory should update message and isLoading state`() = runTest {
        // Given
        val categoryModel = MotherObjectRep.categoryModel
        coEvery { taskRepository.updateCategory(categoryModel) } returns Unit

        // When
        mainScreenViewModel.updateCategory(categoryModel)
        mainScreenViewModel.resetMessages()
        testDispatcher.scheduler.advanceUntilIdle()


        // Then
        coVerify(exactly = 1) { taskRepository.updateCategory(categoryModel) }
        assert(mainScreenViewModel.message.first() == null)
        assert(mainScreenViewModel.isLoading.first() == false) // Ahora pasa
    }

    @Test
    fun `updateCategory should handle exception and update message`() = runTest {
        // Given
        val categoryModel = MotherObjectRep.categoryModel
        val exceptionMessage = "Error creating task"
        coEvery { taskRepository.updateCategory(categoryModel) } throws Exception(exceptionMessage)

        // When
        mainScreenViewModel.updateCategory(categoryModel)
        mainScreenViewModel.resetMessages()

        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { taskRepository.updateCategory(categoryModel) }
        assert(mainScreenViewModel.message.first() == exceptionMessage)
        assert(mainScreenViewModel.isLoading.first() == false) // Ahora pasa
    }

    @Test
    fun `deleteCategory should update message and isLoading state`() = runTest {
        // Given
        val categoryModel = MotherObjectRep.categoryModel
        val expectedMessage = "Se ha eliminado una categoría"

        coEvery { resourceProvider.getString(R.string.newCategoryDeletedMsg) } returns expectedMessage
        coEvery { taskRepository.deleteCategory(categoryModel) } returns Unit

        // When
        mainScreenViewModel.deleteCategory(categoryModel)
        mainScreenViewModel.resetMessages()
        testDispatcher.scheduler.advanceUntilIdle()


        // Then
        coVerify(exactly = 1) { taskRepository.deleteCategory(categoryModel) }
        assert(mainScreenViewModel.message.first() == expectedMessage)
        assert(mainScreenViewModel.isLoading.first() == false)
    }

    @Test
    fun `deleteCategory should handle exception and update message`() = runTest {
        // Given
        val categoryModel = MotherObjectRep.categoryModel
        val exceptionMessage = "Error creating task"
        coEvery { taskRepository.deleteCategory(categoryModel) } throws Exception(exceptionMessage)

        // When
        mainScreenViewModel.deleteCategory(categoryModel)
        mainScreenViewModel.resetMessages()

        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { taskRepository.deleteCategory(categoryModel) }
        assert(mainScreenViewModel.message.first() == exceptionMessage)
        assert(mainScreenViewModel.isLoading.first() == false) // Ahora pasa
    }

}